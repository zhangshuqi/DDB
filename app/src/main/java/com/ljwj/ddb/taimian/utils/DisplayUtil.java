package com.ljwj.ddb.taimian.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by dell on 2016/11/12.
 */
public class DisplayUtil {

    public static int getScreenWidth(Context mContext) {
        WindowManager manager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context mContext) {
        WindowManager manager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }
}
