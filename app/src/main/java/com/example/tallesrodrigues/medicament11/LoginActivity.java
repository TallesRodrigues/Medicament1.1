package com.example.tallesrodrigues.medicament11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText login;
    private EditText password;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login);
        login = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);

        /**/
        Intent mServiceIntent = new Intent(this,AWS.class);
        mServiceIntent.putExtra("Id",login.getText().toString());
        this.startService(mServiceIntent);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this,Acompanhamento.class);
                mainIntent.putExtra("User",login.getText().toString());
                pass = SHA1.sha1Hash(login.getText().toString());
                Log.e("Hash ",pass);
                startActivity(mainIntent);

            }
        });


    }

    @Override
    public void onDestroy(){
       super.onDestroy();
       System.gc();
    }

}
