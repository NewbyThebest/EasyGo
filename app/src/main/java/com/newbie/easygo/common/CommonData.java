package com.newbie.easygo.common;

public class CommonData {
    /**
     * 商品类型
     */
    private String[] tabs = {"安卓手机", "苹果手机", "台式电脑", "笔记本", "平板", "照相机"};

    /**
     * 用户类型，客户还是商家
     */
    private int userType;
    private static CommonData mCommonData;

    /**
     * 当前登陆的用户信息
     */
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

    /**
     * 获取用户类型
     * @return
     */
    public int getUserType() {
        return userType;
    }

    /**
     * 设置用户类型
     * @param userType
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }


    /**
     * 获取类型
     * @return
     */
    public String[] getTabs() {
        return tabs;
    }

    /**
     * 获取当前用户信息
     * @return
     */
    public GoodData getUserInfo() {
        return mUserInfo;
    }

    /**
     * 设置当前用户信息
     * @param mUserInfo
     */
    public void setUserInfo(GoodData mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
