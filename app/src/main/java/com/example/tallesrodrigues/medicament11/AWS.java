package com.example.tallesrodrigues.medicament11;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by TallesRodrigues on 8/18/2016.
 */
public class AWS extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    public static final String serverURL = "http://104.197.208.41:8080";
    public static final String postOpMed = "/get_med";
    DownloaderTask myDownloader;
    DatabaseController databaseController = null;
    Cursor cursorLog = null, cursorData = null;

    public String log_in, pass_word;
    ArrayList<String> medicamento, concentracao, dosagem_tipo, periodo_tipo, duracao_tipo, status, obs;
    ArrayList<Integer> id_consulta, turno_matutino, turno_vespertino, turno_noturno;
    ArrayList<Integer> periodo, duracao, dosagem;

    private class DownloaderTask extends AsyncTask<String, Void, Void> {
        private String Content;
        private final HttpClient Client = new DefaultHttpClient();
        private String Error;
        ContentValues values = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*Initialization*/
            concentracao = new ArrayList<String>();
            dosagem_tipo = new ArrayList<String>();
            periodo_tipo = new ArrayList<String>();
            duracao_tipo = new ArrayList<String>();
            status = new ArrayList<String>();
            obs = new ArrayList<String>();
            id_consulta = new ArrayList<Integer>();
            turno_matutino = new ArrayList<Integer>();
            turno_vespertino = new ArrayList<Integer>();
            turno_noturno = new ArrayList<Integer>();
            medicamento = new ArrayList<String>();
            periodo = new ArrayList<Integer>();
            duracao = new ArrayList<Integer>();
            dosagem = new ArrayList<Integer>();


            /*Prepare  Log in information: email and passaword*/
            if (databaseController == null || cursorLog == null || cursorData == null) {
                databaseController = new DatabaseController(getBaseContext());

                cursorLog = databaseController.loadLog();  //numero de linhas do banco
                cursorData = databaseController.loadData();

                if (values == null) {
                    try {
                        cursorLog.moveToFirst();
                        values = new ContentValues();
                        //   Log.i("Cursor col 1 ", cursorLog.getColumnName(0));
                        //   Log.i("Cursor col 2", cursorLog.getColumnName(1));
                        values.put("email", cursorLog.getString(0));
                        values.put("password", cursorLog.getString(1));
                        values.put("qtd", cursorData.getCount());
                        // Log.i("Email command",values.getAsString("email"));
                        // Log.i("Pass command",values.getAsString("password"));
                    } catch (NullPointerException n) {
                        Log.e("Still null ", n.toString());
                    } catch (IllegalStateException ep) {
                        ep.printStackTrace();
                    }
                }
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(serverURL + postOpMed, ServiceHandler.POST, values);

            Log.i("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray data = jsonObject.getJSONArray("data");

                    if (jsonObject.getBoolean("status")) {

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject current = data.getJSONObject(i);

                            id_consulta.add(current.getInt("id_Consulta"));
                            medicamento.add(current.getString("medicamento"));
                            concentracao.add(current.getString("concentracao"));
                            dosagem.add(current.getInt("dosagem"));
                            dosagem_tipo.add(current.getString("dosagem_tipo"));
                            turno_matutino.add(current.getInt("turno_matutino"));
                            turno_vespertino.add(current.getInt("turno_vespertino"));
                            turno_noturno.add(current.getInt("turno_noturno"));
                            periodo.add(current.getInt("periodo"));
                            periodo_tipo.add(current.getString("periodo_tipo"));
                            duracao.add(current.getInt("duracao"));
                            duracao_tipo.add(current.getString("duracao_tipo"));
                            status.add("active");
                            obs.add(current.getString("obs"));
                            Log.i("Medicamento size ", String.valueOf(medicamento.size()));
                        }
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
                //Log.i("Medicamento Pos Execute ", String.valueOf(medicamento.size()));
                for (int i = 0; i < medicamento.size(); i++) {
                    // Log.i("Medicamento to insert ",medicamento.get(i));
                    String s = databaseController.insertData(id_consulta.get(i), medicamento.get(i), concentracao.get(i),
                            dosagem.get(i), dosagem_tipo.get(i),
                            turno_matutino.get(i), turno_vespertino.get(i), turno_noturno.get(i),
                            periodo.get(i), periodo_tipo.get(i),
                            duracao.get(i), duracao_tipo.get(i),
                            status.get(i), obs.get(i));
                    databaseController = null;
                    cursorLog = null;
                    cursorData = null;
                }

            } catch (NullPointerException n) {
                n.printStackTrace();
            } catch (IndexOutOfBoundsException i) {
                i.printStackTrace();
            }

        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        try {
            log_in = intent.getStringExtra("login");
            pass_word = intent.getStringExtra("password");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onCreate() {
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
                //Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
                new DownloaderTask().execute(serverURL + postOpMed);

                handler.postDelayed(this, 2 * 60000);
            }
        };
        handler.postDelayed(runnable, 2 * 60000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        return Service.START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.i("Service", "Service created by the user");
    }
}
