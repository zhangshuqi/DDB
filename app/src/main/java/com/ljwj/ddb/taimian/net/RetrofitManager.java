package com.ljwj.ddb.taimian.net;

import com.ljwj.ddb.taimian.utils.Constant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2
 * Created by dell on 2017/2/18.
 */

public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;

    private RetrofitManager(){
        initRetrofit();
    }

    public static synchronized RetrofitManager getInstance( ){
        if (mRetrofitManager == null){
                mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private void initRetrofit() {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.URI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }
}
