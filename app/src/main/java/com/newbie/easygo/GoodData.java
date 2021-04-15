package com.newbie.easygo;

import java.io.Serializable;

public class GoodData implements Serializable {

    String uid = "";
    //name
    String title = "";
    //phone
    String price = "";
    //photo
    String imgUrl= "";
    //password
    String seller= "";
    //address
    String category= "";


    GoodData(String title,String price,String url,String seller,String category){
        this.title = title;
        this.price = price;
        this.imgUrl = url;
        this.seller = seller;
        this.category = category;
    }
    GoodData(){

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
