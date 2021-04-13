package com.newbie.easygo;

public class GoodData {
    GoodData(String title,String price,String url,String seller){
        this.title = title;
        this.price = price;
        this.imgUrl = url;
        this.seller = seller;
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

    String title;
    String price;
    String imgUrl;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    String seller;

}
