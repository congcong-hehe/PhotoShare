package com.example.back.pojo;

public class user {
    private String userName;

    private String userPd;

    private String userDes;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPd() {
        return userPd;
    }

    public void setUserPd(String userPd) {
        this.userPd = userPd == null ? null : userPd.trim();
    }

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes == null ? null : userDes.trim();
    }
}