package com.nhombabon.kanatraining;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.nhombabon.kanatraining.utils.BaseActivity;
import com.nhombabon.kanatraining.utils.FormValidationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends BaseActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.name) EditText mNameField;
    @BindView(R.id.email) EditText mEmailField;
    @BindView(R.id.password) EditText mPasswordField;
    @BindView(R.id.link_to_login) TextView linkToLogin;
    @BindView(R.id.btnRegister) Button btnSignUp;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(homeIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FormValidationUtils.clearErrors(mEmailField,mPasswordField);

//                if(FormValidationUtils.isBlank(mNameField)) {
//                    FormValidationUtils.setError(null, mNameField, "Please enter name");
//                    return;
//                }

                if (FormValidationUtils.isBlank(mEmailField)) {
                    FormValidationUtils.setError(null, mEmailField, "Please enter email");
                    return;
                }

                if (!FormValidationUtils.isEmailValid(mEmailField)) {
                    FormValidationUtils.setError(null, mEmailField, "Please enter valid email");
                    return;
                }

                if (TextUtils.isEmpty(mPasswordField.getText())) {
                    FormValidationUtils.setError(null, mPasswordField, "Please enter password");
                    return;
                }

                createUserWithEmailAndPassword(mEmailField.getText().toString(), mPasswordField.getText().toString());

            }
        });
    }

    private void createUserWithEmailAndPassword(String email, String password) {

        Log.d(TAG, "SignUp:" + email);

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            sendEmailVerification(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            showToast("Authentication failed." + task.getException().getMessage());
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

    }

    private void sendEmailVerification(final FirebaseUser user) {
        // [START send_email_verification]
        //final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        if(task.isSuccessful()) {
                            showToast("Verification email sent to " + user.getEmail());
                        }else{
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            showToast("Failed to send verification email.");
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
}
