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
    @POST("UpdateUserInfo")
    Observable<Boolean> updateUserInfo(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("uoloadImg")
    Observable<GoodData> uploadImg(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("queryCategoryGoods")
    Observable<String> queryCategoryGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("queryBuyerGoods")
    Observable<String> queryBuyerGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("querySellerGoods")
    Observable<String> querySellerGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("addGoods")
    Observable<String> addGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("updateGoods")
    Observable<String> updateGoods(@FieldMap Map<String,  String> map);

    @FormUrlEncoded
    @POST("deleteGoods")
    Observable<String> deleteGoods(@FieldMap Map<String,  String> map);

}
