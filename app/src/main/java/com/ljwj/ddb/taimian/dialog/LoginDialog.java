package com.ljwj.ddb.taimian.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ljwj.ddb.taimian.R;

/**
 * Created by dell on 2017/2/18.
 */

public class LoginDialog extends Dialog {
    private LinearLayout linearlayout;

    public LoginDialog(Context context) {
        super(context,R.style.loadingDialogStyle);
    }

    public LoginDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logindialog);
        linearlayout=(LinearLayout)findViewById(R.id.linearlayout);
        linearlayout.getBackground().setAlpha(210);
    }
}
