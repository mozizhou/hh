package com.ruoyi.startproject.domain;

import java.util.List;

public class ProjectTree {
    private String company;
    private List<Project> projectNames;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Project> getProjectNames() {
        return projectNames;
    }

    public void setProjectNames(List<Project> projectNames) {
        this.projectNames = projectNames;
    }

    public ProjectTree(String company, List<Project> projectNames) {
        this.company = company;
        this.projectNames = projectNames;
    }

    public ProjectTree() {
    }
}
