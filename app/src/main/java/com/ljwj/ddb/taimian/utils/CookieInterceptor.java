package com.ljwj.ddb.taimian.utils;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * cookie拦截器
 * Created by dell on 2017/2/25.
 */

public class CookieInterceptor {

    //OkHttpClient添加取出cookie的拦截器
    public static OkHttpClient getOKHttpClien(Context context) {
        OkHttpClient client=new OkHttpClient
                .Builder()
                // .addInterceptor(new SaveCookiesInterceptor(this))
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();
        return client;
    }

    //OkHttpClient添加保存cookie的拦截器
    public static OkHttpClient setOKHttpClien(Context context) {
        OkHttpClient client=new OkHttpClient
                .Builder()
                .addInterceptor(new SaveCookiesInterceptor(context))
                // .addInterceptor(new AddCookiesInterceptor(this))
                .build();
        return client;
    }
}
