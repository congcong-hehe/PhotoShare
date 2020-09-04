package com.example.back.pojo;

import java.util.Date;

public class photo {
    private String location;

    private String userName;

    private String photoTag;

    private Date storeTime;

    private Integer photoLikes;

    private Integer photoCollects;

    private String photoDes;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhotoTag() {
        return photoTag;
    }

    public void setPhotoTag(String photoTag) {
        this.photoTag = photoTag == null ? null : photoTag.trim();
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    public Integer getPhotoLikes() {
        return photoLikes;
    }

    public void setPhotoLikes(Integer photoLikes) {
        this.photoLikes = photoLikes;
    }

    public Integer getPhotoCollects() {
        return photoCollects;
    }

    public void setPhotoCollects(Integer photoCollects) {
        this.photoCollects = photoCollects;
    }

    public String getPhotoDes() {
        return photoDes;
    }

    public void setPhotoDes(String photoDes) {
        this.photoDes = photoDes == null ? null : photoDes.trim();
    }
}