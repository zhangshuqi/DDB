package com.ljwj.ddb.taimian.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/*
 *  @项目名：  DDB 
 *  @包名：    com.ljwj.ddb.taimian.utils
 *  @文件名:   Dp2PxUtils
 *  @创建者:   Administrator
 *  @创建时间:  2017/3/4 0004 03:26
 *  @描述：    TODO
 */
public class Dp2PxUtils {
    private static final String TAG = "Dp2PxUtils";

    /**
     * dp2px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px2dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     *根据设备信息获取当前分辨率下指定单位对应的像素大小；
     * px,dip,sp -> px
     */
    public float getRawSize(Context c, int unit, float size) {
        Resources r;
        if (c == null){
            r = Resources.getSystem();
        }else{
            r = c.getResources();
        }
        return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
    }
}
