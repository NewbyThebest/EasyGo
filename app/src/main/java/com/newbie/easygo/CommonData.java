package com.newbie.easygo;

class CommonData {
    private String[] tabs = {"安卓手机", "苹果手机", "台式电脑", "笔记本", "平板", "照相机"};

    private int userType;
    private static CommonData mCommonData = new CommonData();

    private CommonData() {

    }

    public static CommonData getCommonData() {
        return mCommonData;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }


    public String[] getTabs() {
        return tabs;
    }
}
