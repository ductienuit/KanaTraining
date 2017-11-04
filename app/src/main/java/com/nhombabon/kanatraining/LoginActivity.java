package com.nhombabon.kanatraining;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText email, password;

    private TextView forgottenPassword, signInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        forgottenPassword = (TextView)findViewById(R.id.forgotten_password);
        signInLink = (TextView)findViewById(R.id.link_to_registration);

        forgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signInIntent);
            }
        });

        Button loginButton = (Button)findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(homeIntent);
                Intent signInIntent = new Intent(LoginActivity.this, QuizSelectListActivity.class);
                startActivity(signInIntent);


            }
        });
    }
}
