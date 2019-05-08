package com.example.myapplicationconform;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPIService {
    // user 註冊（第一次開啟ＡＰＰ先註冊，權限：0(遊客)、獲得一個ＩＤ <= 真正目的（綁定手機））
    @GET("user")
    Call<userSchema> getUser();

    @FormUrlEncoded
    @POST("login/{Uid}")
    Call<userSchema> login(@Path("Uid")int Uid, @Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/{Uid}")
    Call<userSchema> register(@Path("Uid")int Uid, @Field("name") String name, @Field("password") String password);

    @GET("product/{id}")
    Call<productSchema> getProduct(@Path("id") int id);

    @GET("productName")
    Call<ResponseBody> getProductName();


    @GET("productList")
    Call<ResponseBody> getProductList();

    @GET("node")
    Call<nodeSchema> getnode();

    @FormUrlEncoded
    @POST("feedback/{Uid}/{Pid}")
    Call<feedbackSchema> feedback(@Path("Uid") int Uid, @Path("Pid") int Pid , @Field("feedback") String feedback);

    @GET("{Uid}/{Pid}")
    Call<pathSchema> getpath(@Path("Uid")int Uid,@Path("Pid")int Pid);

    @GET("path/{Uid}")
    Call<pathSchema> getPathUq(@Path("Uid")int Uid);

    @GET("suggest")
    Call<pathSchema> getSuggest();

    @GET("wantted/{Uid}")
    Call<pathSchema> getWantted(@Path("Uid")int Uid);
}
