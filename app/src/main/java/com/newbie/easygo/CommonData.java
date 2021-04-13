package com.newbie.easygo;

class CommonData {


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


}
