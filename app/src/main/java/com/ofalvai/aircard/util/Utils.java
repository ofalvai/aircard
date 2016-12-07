package com.ofalvai.aircard.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Utils {

    public static int convertDpToPx(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

}
