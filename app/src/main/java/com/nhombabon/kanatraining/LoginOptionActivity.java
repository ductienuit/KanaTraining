package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginOptionActivity extends AppCompatActivity {

    private static final String TAG = LoginOptionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);


        Button signInButton = (Button)findViewById(R.id.sign_in);
        Button signUpButton = (Button)findViewById(R.id.sign_up);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(LoginOptionActivity.this, LoginActivity.class);
                startActivity(signInIntent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(LoginOptionActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
