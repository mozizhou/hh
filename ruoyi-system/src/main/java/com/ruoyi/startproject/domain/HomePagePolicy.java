package com.ruoyi.startproject.domain;


import com.ruoyi.common.core.domain.BaseEntity;



public class HomePagePolicy extends BaseEntity {

    /**
     * 首页政策 home_page_policy
     *
     * @author ruoyi
     * @date 2025-06-17
     */


    private static final long serialVersionUID = 1L;

    /**
     * 政策列表
     */
    private Long id;
    private String title;
    private String introduction;
    private String link;
    private String origin;
    private String picture;
    private String createdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public HomePagePolicy(Long id, String title, String introduction, String link, String origin, String picture, String createdate) {
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.link = link;
        this.origin = origin;
        this.picture = picture;
        this.createdate = createdate;
    }

    public HomePagePolicy() {

    }




}


