package com.example.tallesrodrigues.medicament11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton,signupButton;
    private EditText login;
    private EditText password;
    private String pass;
    DatabaseController crud ;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "log_memory" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login);
        login = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        signupButton =(Button)findViewById(R.id.signupButton);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String restored = sharedPreferences.getString("login",null);
        if (restored!=null){
          login.setText(sharedPreferences.getString("login",""));
          password.setText(sharedPreferences.getString("password",""));
        }

        /*Start Service to load data on background*/
        Intent mServiceIntent = new Intent(this,AWS.class);
        mServiceIntent.putExtra("login",login.getText().toString());
        mServiceIntent.putExtra("password",pass);
        this.startService(mServiceIntent);




        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this,Acompanhamento.class);
                mainIntent.putExtra("User",login.getText().toString());
                pass = SHA1.sha1Hash(login.getText().toString());
                Log.e("Hash ",pass);
                startActivity(mainIntent);

            }
        });

        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("login", login.getText().toString());
                ed.putString("password",password.getText().toString());
                ed.apply();
                Toast.makeText(getApplication(),"Succeed",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onDestroy(){
       super.onDestroy();
       System.gc();
    }

}
