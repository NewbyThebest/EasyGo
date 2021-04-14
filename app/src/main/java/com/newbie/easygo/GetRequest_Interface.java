package com.newbie.easygo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetRequest_Interface {
    @FormUrlEncoded
    @POST("/EasyGo/servlet/DataResponse")
    Call<GoodData> getCall(@FieldMap Map<String,  String> map);
    // @GET注解的作用:采用Get方法发送网络请求
}
