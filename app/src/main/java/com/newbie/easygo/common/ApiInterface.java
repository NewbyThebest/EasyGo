package com.newbie.easygo.common;

import java.util.List;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiInterface {
    /**
     * 查询用户信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("CheckUserInfo")
    Observable<GoodData> checkUserInfo(@FieldMap Map<String,  String> map);

    /**
     * 添加用户信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("addUserInfo")
    Observable<Boolean> addUserInfo(@FieldMap Map<String,  String> map);

    /**
     * 更新用户信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("UpdateUserInfo")
    Observable<Boolean> updateUserInfo(@FieldMap Map<String,  String> map);

    /**
     * 上传图片到服务器
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("uoloadImg")
    Observable<GoodData> uploadImg(@FieldMap Map<String,  String> map);

    /**
     * 查询客户类型的类别下的商品列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("queryCategoryGoods")
    Observable<List<GoodData>> queryCategoryGoods(@FieldMap Map<String,  String> map);

    /**
     * 查询商家类型的类别下的商品列表
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("querySellerCategoryGoods")
    Observable<List<GoodData>> querySellerCategoryGoods(@FieldMap Map<String,  String> map);

    /**
     * 查询客户已经购买的商品
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("queryBuyerGoods")
    Observable<List<GoodData>> queryBuyerGoods(@FieldMap Map<String,  String> map);

    /**
     * 查询商家售出的商品
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("querySellerGoods")
    Observable<List<GoodData>> querySellerGoods(@FieldMap Map<String,  String> map);

    /**
     * 查询客户的轮播图商品信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("queryBannerGoods")
    Observable<List<GoodData>> queryBannerGoods(@FieldMap Map<String,  String> map);

    /**
     * 查询商家的轮播图商品信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("querySellerBannerGoods")
    Observable<List<GoodData>> querySellerBannerGoods(@FieldMap Map<String,  String> map);

    /**
     * 添加商品
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("addGoodsInfo")
    Observable<Boolean> addGoods(@FieldMap Map<String,  String> map);

    /**
     * 更新商品信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("updateGoodsInfo")
    Observable<Boolean> updateGoods(@FieldMap Map<String,  String> map);

    /**
     * 删除商品信息
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("deleteGoods")
    Observable<Boolean> deleteGoods(@FieldMap Map<String,  String> map);

}
