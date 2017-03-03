package com.ljwj.ddb.taimian.utils;

import com.ljwj.ddb.taimian.bean.ADDClientBean;
import com.ljwj.ddb.taimian.bean.UserData;
import com.ljwj.ddb.taimian.bean.UserInfo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/2/17.
 */
public interface ResponseInfoAPI {

    @POST(Constant.URILOGIN)
    @FormUrlEncoded
    Call<UserInfo>  getUserInfo(@Field("mobile") String mobile, @Field("password") String password );

    @GET(Constant.URIGETUSER)
    Call<UserData>  getUserData();

    @POST(Constant.URIToken)
    @FormUrlEncoded
    Call<UserInfo>  getUserToken(@Field("token") String token);

    @POST(Constant.URIADDCLIENT)
    @FormUrlEncoded
    Call<ADDClientBean> getAddClient(@Field("contacttype") Integer contacttype
            , @Field("name") String name
            , @Field("sex") Integer sex
            , @Field("phone") String phone
            , @Field("address") String address
            , @Field("type") Integer type );

}
