package com.napier.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        close = (ImageView) findViewById(R.id.ic_close);
        initialise();
    }

    private void initialise(){

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPasswordValid() && isPasswordIdentical() && isEmailAddressValid()){
                    Intent mapActivity = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivityForResult(mapActivity, 1);
                }
            }
        });

        final EditText nameField = (EditText) findViewById(R.id.editText);
        final EditText usernameField = (EditText) findViewById(R.id.editText2);

        nameField.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(30),
        });

        usernameField.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(20),
        });
    }

    private boolean isEmailAddressValid(){
        final EditText emailField = (EditText) findViewById(R.id.editText3);
        String text = emailField.getText().toString();

        boolean hasSymbol = false;

        if(text.contains("@")){
            hasSymbol = true;
        }

        if(hasSymbol){
            return true;
        }else{
            Toast.makeText(getBaseContext(), "Email address must contain a '@' symbol " , Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isPasswordValid(){
        final EditText passwordField = (EditText) findViewById(R.id.editText4);
        String text = passwordField.getText().toString();

        int textLength = text.length();
        boolean hasLetters= false;
        boolean hasNumbers = false;
        boolean overEightCharacters = false;

        for(int i = 0; i<text.length(); i++)
            if(Character.isLetter(text.charAt(i))) {
                hasLetters = true;
            } else if (Character.isDigit(text.charAt(i))) {
                hasNumbers = true;
            }

        if(textLength >= 8){
            overEightCharacters = true;
        }

        if(hasLetters && hasNumbers && overEightCharacters){
            return true;
        }else{
            Toast.makeText(getBaseContext(), "Passwords should be 8 characters and contain both letters and numbers.", text.length()).show();
            return false;
        }
    }

    private boolean isPasswordIdentical(){
        final EditText enterPassword = (EditText) findViewById(R.id.editText4);
        String password = enterPassword.getText().toString();

        final EditText confirmPassword = (EditText) findViewById(R.id.editText5);
        String confirm = confirmPassword.getText().toString();

        if(password.equals(confirm)){
            return true;
        }
        Toast.makeText(getBaseContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void closeActivity(){
        super.onBackPressed();
    }
}
