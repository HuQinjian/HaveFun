package com.example.havefun;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

/**
 * @Project: HaveFun
 * @Author: hqj
 * @Date: 2023/3/16 17:39
 * @Introduce:
 **/

public class MyDialog extends AlertDialog.Builder {

    private Context context = null;
    private String title = "";
    private String massage = "";
    private String negative = "";
    private String positive = "";

    public MyDialog(@NonNull Context context) {
        super(context);
        this.context = context;

    }
    public void ShowDialog(String title, String massage, String negative, String positive){
        this.title = title;
        this.massage  = massage ;
        this.negative = negative;
        this.positive = positive;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(massage)
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }
}
