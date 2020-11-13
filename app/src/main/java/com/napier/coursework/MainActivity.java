package com.napier.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }

    private void initialise(){
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivity = new Intent(MainActivity.this, MapActivity.class);
                startActivityForResult(mapActivity, 1);
            }
        });

        TextView txtRegister = (TextView) findViewById(R.id.textView3);
        txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

    }
}
