package com.newbie.easygo;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("CheckUserInfo")
    Observable<GoodData> checkUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("addUserInfo")
    Observable<Boolean> addUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("updateUserInfo")
    Observable<String> updateUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("uoloadImg")
    Observable<String> uploadImg(@FieldMap Map<String,  String> map);

}
