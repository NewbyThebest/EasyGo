package com.newbie.easygo;

import java.util.ArrayList;
import java.util.List;

class CommonData {
    private String[] tabs = {"安卓手机", "苹果手机", "台式电脑", "笔记本", "平板", "照相机"};

    private int userType;
    private static CommonData mCommonData;

    private GoodData mUserInfo = new GoodData();

    private CommonData() {

    }

    public static CommonData getCommonData() {

        if (mCommonData == null){
            synchronized (CommonData.class){
                if (mCommonData == null){
                    mCommonData = new CommonData();
                }
            }
        }
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

    public GoodData getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(GoodData mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
