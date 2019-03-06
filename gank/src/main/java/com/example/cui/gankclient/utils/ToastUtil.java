package com.example.cui.gankclient.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Cui on 2018/9/10.
 */

public class ToastUtil {

    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
