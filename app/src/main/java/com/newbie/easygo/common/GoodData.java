package com.newbie.easygo.common;

import java.io.Serializable;

/**
 * 数据bean，用户信息和商品信息都用这个
 */
public class GoodData implements Serializable {

    /**
     * 用户或商品id
     */
    public String uid = "";
    /**
     * 用户名称或商品标题
     */
    public String title = "";

    /**
     * 手机号码或者商品价格
     */
    public String price = "";

    /**
     * 用户头像或商品预览图
     */
    public String imgUrl= "";

    /**
     * 用户面或者卖家名称
     */
    public String seller= "";

    /**
     * 用户地址或者商家地址
     */
    public String category= "";

    /**
     * 商家id
     */
    public String sellerId = "";

    /**
     * 购买者id
     */
    public String buyerId = "";


    GoodData(String title,String price,String url,String seller,String category){
        this.title = title;
        this.price = price;
        this.imgUrl = url;
        this.seller = seller;
        this.category = category;
    }
    public GoodData(){

    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



}
