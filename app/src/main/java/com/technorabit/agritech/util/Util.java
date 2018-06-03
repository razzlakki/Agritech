package com.technorabit.agritech.util;

import android.content.res.Resources;

/**
 * Created by raja on 02/06/18.
 */

public class Util {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
