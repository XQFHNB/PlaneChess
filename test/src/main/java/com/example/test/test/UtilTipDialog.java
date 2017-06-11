package com.example.test.test;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.test.R;

/**
 * @author XQF
 * @created 2017/6/9
 */
public class UtilTipDialog {

    public static AlertDialog.Builder showDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_content, null, false);
        builder.setView(view);
        builder.create();
        builder.show();
        return builder;
    }
}
