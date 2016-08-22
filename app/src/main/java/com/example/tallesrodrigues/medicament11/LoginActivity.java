package com.example.tallesrodrigues.medicament11;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton,signupButton;
    private EditText login;
    private EditText password;

    DatabaseController crud ;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "log_memory" ;
    public static final String serverURL = "http://104.197.208.41:8080";
    public static final String postOpSignup = "/sign_up";
    public static final String postOpLogin = "/login";
    boolean isValid = false;
    private String email, pass, authentication = "";
    int ope;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login);
        login = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        signupButton =(Button)findViewById(R.id.signupButton);

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        crud = new DatabaseController(getBaseContext());

        String restored = sharedPreferences.getString("email", null);
        if (restored!=null){
            login.setText(sharedPreferences.getString("email", ""));
          password.setText(sharedPreferences.getString("password",""));
        }


        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                email = login.getText().toString();
                pass = password.getText().toString();
                ope = 1;
                signup();

            }
        });

        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                email = login.getText().toString();
                pass = password.getText().toString();
                ope = 2;
                signup();
                if (isValid) {
                    startAcompanhamento();
                    // startAWS();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void startAcompanhamento() {
        Intent mainIntent = new Intent(LoginActivity.this, Acompanhamento.class);
        mainIntent.putExtra("login", login.getText().toString());
        startActivity(mainIntent);
    }

    public void startAWS() {
        /*Start Service to load data on background*/
        Intent mServiceIntent = new Intent(this, AWS.class);
        mServiceIntent.putExtra("email", login.getText().toString());
        mServiceIntent.putExtra("password", pass);
        this.startService(mServiceIntent);
    }

    public void signup() {

        progressDialog.setIndeterminate(true);

        progressDialog.setMessage("Processing...");
        progressDialog.show();

        if (ope == 1) {
            authentication = "login";
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            new sendLogInfo().execute(serverURL + login);
                            //onSignupSuccess();
                            // onSignupFailed();
                        }
                    }, 3000);
        } else {
            if (ope == 2) {
                authentication = "signup";
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                new sendLogInfo().execute(serverURL + postOpSignup);
                                //onSignupSuccess();
                                // onSignupFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        }

    }

    public void onSignupSuccess() {
        setResult(RESULT_OK, null);

    }

    private class sendLogInfo extends AsyncTask<String, Void, Void> {
        private String Content;
        private final HttpClient Client = new DefaultHttpClient();
        private String Error;
        ContentValues values;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            values = new ContentValues();
            try {
                values.put("email", email);
                values.put("password", pass);
                values.put("qtd", 0);
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

            if (ope == 1) {
                jsonStr = sh.makeServiceCall(serverURL + postOpLogin, ServiceHandler.POST, values);
            } else {
                if (ope == 2) {
                    jsonStr = sh.makeServiceCall(serverURL + postOpSignup, ServiceHandler.POST, values);
                }
            }

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
            //Log.e("WebService Returned", "Output: "+ Content);
            try {
                SharedPreferences.Editor ed = sharedPreferences.edit();
                Log.i("authentication", authentication);
                if (isValid) {
                    ed.putString("email", email);
                    ed.putString("password", pass);
                    ed.apply();
                    crud.insertData(email, pass);

                    startAcompanhamento();
                    progressDialog.dismiss();
                    // startAWS();

                }
                progressDialog.dismiss();
                if (!isValid) {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            } catch (NullPointerException n) {
                n.printStackTrace();
            }

        }
    }

    @Override
    public void onDestroy(){
       super.onDestroy();
       System.gc();
    }

}
