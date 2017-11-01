package com.nhombabon.kanatraining;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText fullname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fullname = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        TextView linkToLogin = (TextView)findViewById(R.id.link_to_login);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(homeIntent);
            }
        });

        Button signUpButton = (Button)findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

    }
}
