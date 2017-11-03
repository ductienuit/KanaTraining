package com.nhombabon.kanatraining.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.nhombabon.kanatraining.R;

public class AppTransparentDialog extends Dialog {

   public AppTransparentDialog(Context context) {
       super(context, R.style.Theme_ThemeTransparent);
  }

    public AppTransparentDialog(Context context, View view) {
        super(context, R.style.Theme_ThemeTransparent);
        setContentView(view);
    }

    public AppTransparentDialog(Context context, View view, LayoutParams lp) {
        super(context, R.style.Theme_ThemeTransparent);
        setContentView(view, lp);
    }
}
