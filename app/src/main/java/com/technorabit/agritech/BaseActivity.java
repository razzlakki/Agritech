package com.technorabit.agritech;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by raja on 02/06/18.
 */

public class BaseActivity extends AppCompatActivity {



    public static void startActivity(Context context, Class<?> destination, boolean isCloseBase) {
        Intent intent = new Intent(context, destination);
        context.startActivity(intent);
        ((BaseActivity) context).overridePendingTransition(0, 0);
        if (isCloseBase)
            ((BaseActivity) context).finish();
    }
}
