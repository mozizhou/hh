package com.ruoyi.startproject.domain;

public class Project {
    private String projectName;
    private Long id;
    private String projectNo;

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }


    public Project(String projectName, Long id, String projectNo) {
        this.projectName = projectName;
        this.id = id;
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project(String projectName, Long id) {
        this.projectName = projectName;
        this.id = id;
    }

    public Project(String projectName,String projectNo, Long id) {
        this.projectName = projectName;
        this.id = id;
        this.projectNo = projectNo;
    }

    public Project() {
    }
}
