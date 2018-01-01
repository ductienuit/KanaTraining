package com.nhombabon.kanatraining;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nhombabon.kanatraining.utils.BaseActivity;
import com.nhombabon.kanatraining.utils.FormValidationUtils;

/**
 * Created by hoangkhanh on 12/8/17.
 */

public class ForgotPasswordActivity extends BaseActivity {

    private EditText mEmailField;
    private Button btnSubmit;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmailField = (EditText) findViewById(R.id.email);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormValidationUtils.clearErrors(mEmailField);

                if (FormValidationUtils.isBlank(mEmailField)) {
                    FormValidationUtils.setError(null, mEmailField, "Please enter email");
                    return;
                }

                if (!FormValidationUtils.isEmailValid(mEmailField)) {
                    FormValidationUtils.setError(null, mEmailField, "Please enter valid email");
                    return;
                }

                mAuth.sendPasswordResetEmail(mEmailField.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "We have sent you instructions to reset your password", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });

    }
}
