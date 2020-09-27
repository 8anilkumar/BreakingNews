package com.anilkumarnishad.breakingnews;

import android.app.Dialog;
import android.content.Context;

public class CustomLoader extends Dialog {

    public CustomLoader(Context context) {
        super(context);
    }

    public CustomLoader(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.progress_view);
    }

    public CustomLoader(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
