package com.example.tallesrodrigues.medicament11;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class CardViewBig extends AppCompatActivity {

    TextView medicamentoBig, dosagemBig, periodoBig, concentracaoBig;
    FloatingActionButton tomar, feedback;
    TextView feedbackMessage;
    String id_Consulta;
    DatabaseController crud;
    public static final String serverURL = "http://104.197.208.41:8080";
    public static final String postOpSignup = "/send_msg";
    boolean isValid = false;
    private String email, pass, medicine = "";
    String msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view_big);
        Intent result = getIntent();

        medicamentoBig = (TextView) findViewById(R.id.medicamentoBig);
        tomar = (FloatingActionButton) findViewById(R.id.tomar);
        feedback = (FloatingActionButton) findViewById(R.id.feedback);
        dosagemBig = (TextView) findViewById(R.id.dosagemBig);
        concentracaoBig = (TextView) findViewById(R.id.concentracaoBig);
        periodoBig = (TextView) findViewById(R.id.periodoBig);
        feedbackMessage = (TextView) findViewById(R.id.feedbackMessage);

        medicamentoBig.setText(result.getStringExtra("Medicamento"));
        dosagemBig.setText(result.getStringExtra("dosagem"));
        concentracaoBig.setText(result.getStringExtra("concentracao"));
        periodoBig.setText(result.getStringExtra("periodo"));

        //get Id consulta
        id_Consulta = result.getStringExtra("id_Consulta");

        //get login credentials
        crud = new DatabaseController(getBaseContext());
        Cursor aux = crud.loadLog();
        aux.moveToFirst();
        email = aux.getString(0);
        pass = aux.getString(1);

        //get comment


        //Get Medicine
        medicine = medicamentoBig.getText().toString();

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageToDoctor();
            }
        });

        tomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminder(getBaseContext());
                setNotification(getBaseContext());
            }
        });


    }

    public void setReminder(Context context) {
          /*  Calendar updateTime = Calendar.getInstance();
            updateTime.setTimeZone(TimeZone.getDefault());
            updateTime.set(Calendar.HOUR_OF_DAY, 2);
            updateTime.set(Calendar.MINUTE, 4);
            Intent downloader = new Intent(context, MyStartServiceReceiver.class); //broadcast
            downloader.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), 1000 * 60 * 2 , pendingIntent);

            Log.i("MyActivity", "Set alarmManager.setRepeating to: " + updateTime.getTime().toLocaleString()); */

        setNotification(context);

    }

    public void setNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intentTomar = new Intent(this, NotificationReceiver.class);
        intentTomar.putExtra("code", 1);
        Intent intentDepois = new Intent(this, NotificationReceiver.class);
        intentDepois.putExtra("code", 2);

        int notificationId = 1;

        PendingIntent pIntentTomar = PendingIntent.getActivity(this, notificationId, intentTomar, 0);
        PendingIntent pIntentDepois = PendingIntent.getActivity(this, 2, intentDepois, 0);

        Notification n = new Notification.Builder(this)
                .setContentTitle("Medicament")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText("Rivotril")
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_done_white_24dp, "Tomar", pIntentTomar)
                .addAction(R.drawable.ic_clear_white_24dp, "Depois", pIntentDepois).build();

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //n.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notificationId, n);

    }


    public void sendMessageToDoctor() {
        // new android.os.Handler().postDelayed(
        //         new Runnable() {
        //              public void run() {
        new sendLogInfo().execute(serverURL + email);
        //onSignupSuccess();
        // onSignupFailed();
        //              }
        //          }, 3000);

    }

    private class sendLogInfo extends AsyncTask<String, Void, Void> {
        private String Content;
        private final HttpClient Client = new DefaultHttpClient();
        private String Error;
        ContentValues values;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            msg = feedbackMessage.getText().toString();

            Log.i("email", email);
            Log.i("password", pass);
            Log.i("id_Consulta", id_Consulta);
            Log.i("Medicamento", medicine);
            Log.i("Msg", msg);

            msg = msg + "causada por " + medicine;

            values = new ContentValues();
            try {
                values.put("email", email);
                values.put("password", pass);
                values.put("id_consulta", id_Consulta);
                values.put("msg", msg);
            } catch (NullPointerException n) {
                Log.e("Still null ", n.toString());
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            try {
                Log.i("Login sent ", values.getAsString("email"));
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            String jsonStr = "";

            jsonStr = sh.makeServiceCallFeedback(serverURL + postOpSignup, ServiceHandler.POST, values);

            Log.i("Response: ", "> " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    if (jsonObject.getBoolean("status")) {
                        Log.i("Status", jsonObject.getString("msg"));
                        isValid = true;
                    } else {
                        isValid = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (isValid) {
                Log.i("Operation ", "Message sent to doctor");
                Toast.makeText(getBaseContext(), "Message sent to doctor",
                        Toast.LENGTH_LONG).show();
            } else {
                Log.i("Operation ", "Message Not sento to doctor");
                Toast.makeText(getBaseContext(), "Message not sent to doctor",
                        Toast.LENGTH_LONG).show();
            }

        }
    }


}
