package com.nhombabon.kanatraining.utils;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ScrollView;

public class FormValidationUtils {

    public static boolean isBlank(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    public static boolean isEmailValid(EditText editText) {
        return Patterns.EMAIL_ADDRESS.matcher(editText.getText()).matches();
    }

    public static void clearErrors(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }

    public static void setError(final ScrollView scrollView, final EditText editText, String errorMessage) {
        editText.requestFocus();
        if (scrollView != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollView.smoothScrollTo(0, editText.getBottom());
                }
            });
        }
        editText.setError(errorMessage);
    }

    public static void clearForm(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setText("");
        }
    }

}
