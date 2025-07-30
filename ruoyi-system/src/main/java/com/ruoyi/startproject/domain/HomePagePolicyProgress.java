package com.ruoyi.startproject.domain;

public class HomePagePolicyProgress {
    private String title;
    private String introduction;
    private String link;
    private String origin;
    private String picture;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public HomePagePolicyProgress(String title, String introduction, String link, String origin, String picture) {
        this.title = title;
        this.introduction = introduction;
        this.link = link;
        this.origin = origin;
        this.picture = picture;
    }


    public HomePagePolicyProgress() {
    }




}
