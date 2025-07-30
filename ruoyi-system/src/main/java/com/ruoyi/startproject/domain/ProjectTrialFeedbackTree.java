package com.ruoyi.startproject.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class ProjectTrialFeedbackTree extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private  Long index;
    private List<ProjectTrialFeedback> testList;

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public List<ProjectTrialFeedback> getTestList() {
        return testList;
    }

    public void setTestList(List<ProjectTrialFeedback> testList) {
        this.testList = testList;
    }
    public ProjectTrialFeedbackTree(Long index, List<ProjectTrialFeedback> text) {
        this.index = index;
        this.testList = text;
    }
    public ProjectTrialFeedbackTree() {
    }
}
