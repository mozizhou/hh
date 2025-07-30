package com.ruoyi.startproject.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 project_trial_main
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public class ProjectTrialMain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long projectId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String company;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date trialDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productFeatures;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String trialCompany;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long status;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productName;

    private String file;

    private List<ProjectTrialFeedbackTree> testFeedList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }

    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setTrialDate(Date trialDate) 
    {
        this.trialDate = trialDate;
    }

    public Date getTrialDate() 
    {
        return trialDate;
    }

    public void setProductFeatures(String productFeatures) 
    {
        this.productFeatures = productFeatures;
    }

    public String getProductFeatures() 
    {
        return productFeatures;
    }

    public void setTrialCompany(String trialCompany) 
    {
        this.trialCompany = trialCompany;
    }

    public String getTrialCompany() 
    {
        return trialCompany;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public List<ProjectTrialFeedbackTree> getTestFeedList() {
        return testFeedList;
    }

    public void setTestFeedList(List<ProjectTrialFeedbackTree> testFeedList) {
        this.testFeedList = testFeedList;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ProjectTrialMain(Long id, Long projectId, String company, String projectName, Date trialDate, String productFeatures, String trialCompany, Long status, String productName, String file, List<ProjectTrialFeedbackTree> testFeedList) {
        this.id = id;
        this.projectId = projectId;
        this.company = company;
        this.projectName = projectName;
        this.trialDate = trialDate;
        this.productFeatures = productFeatures;
        this.trialCompany = trialCompany;
        this.status = status;
        this.productName = productName;
        this.file = file;
        this.testFeedList = testFeedList;
    }

    public ProjectTrialMain(Long id, Long projectId, String company, String projectName, Date trialDate, String productFeatures, String trialCompany, Long status, String productName, List<ProjectTrialFeedbackTree> testFeedList) {
        this.id = id;
        this.projectId = projectId;
        this.company = company;
        this.projectName = projectName;
        this.trialDate = trialDate;
        this.productFeatures = productFeatures;
        this.trialCompany = trialCompany;
        this.status = status;
        this.productName = productName;
        this.testFeedList = testFeedList;
    }

    public ProjectTrialMain() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("company", getCompany())
            .append("projectName", getProjectName())
            .append("trialDate", getTrialDate())
            .append("productFeatures", getProductFeatures())
            .append("trialCompany", getTrialCompany())
            .append("status", getStatus())
            .append("productName", getProductName())
            .toString();
    }
}
