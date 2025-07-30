package com.ruoyi.startproject.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 试制终审报告子对象 project_trial_production_finalreport
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public class ProjectTrialProductionFinalreport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 试制ID */
    @Excel(name = "试制ID")
    private Long trialid;
    /**
     * 主表状态
     */
    private String status;
    /**
     * 公司
     */
    private String company;
    private String quantity;

    private String projectName;
    private Long projectId;
    private String projectNo;
    private String trialProducer;

    /** 反馈部门 */
    @Excel(name = "反馈部门")
    private String feedbackDepartment;

    /** 问题等级 (A/B/C) */
    @Excel(name = "问题等级 (A/B/C)")
    private String issueLevel;

    /** 原因分析 */
    @Excel(name = "原因分析")
    private String causeAnalysis;

    /** 问题描述 */
    @Excel(name = "问题描述")
    private String descriptionProblem;

    /** 临时解决方案 */
    @Excel(name = "临时解决方案")
    private String temporarySolution;

    /** 长期措施 */
    @Excel(name = "长期措施")
    private String longtermMeasures;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String responsibleDepartment;

    /** 责任人 */
    @Excel(name = "责任人")
    private String responsiblePerson;

    /** 计划完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedTime;

    /** 最新状态 */
    @Excel(name = "最新状态")
    private String latestStatus;

    /** 实际完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualTime;

    /** 反馈验证人 */
    @Excel(name = "反馈验证人")
    private String feedbackValidators;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTrialid(Long trialid) 
    {
        this.trialid = trialid;
    }

    public Long getTrialid() 
    {
        return trialid;
    }

    public void setFeedbackDepartment(String feedbackDepartment) 
    {
        this.feedbackDepartment = feedbackDepartment;
    }

    public String getFeedbackDepartment() 
    {
        return feedbackDepartment;
    }

    public void setIssueLevel(String issueLevel) 
    {
        this.issueLevel = issueLevel;
    }

    public String getIssueLevel() 
    {
        return issueLevel;
    }

    public void setCauseAnalysis(String causeAnalysis) 
    {
        this.causeAnalysis = causeAnalysis;
    }

    public String getCauseAnalysis() 
    {
        return causeAnalysis;
    }

    public void setDescriptionProblem(String descriptionProblem) 
    {
        this.descriptionProblem = descriptionProblem;
    }

    public String getDescriptionProblem() 
    {
        return descriptionProblem;
    }

    public void setTemporarySolution(String temporarySolution) 
    {
        this.temporarySolution = temporarySolution;
    }

    public String getTemporarySolution() 
    {
        return temporarySolution;
    }

    public void setLongtermMeasures(String longtermMeasures) 
    {
        this.longtermMeasures = longtermMeasures;
    }

    public String getLongtermMeasures() 
    {
        return longtermMeasures;
    }

    public void setResponsibleDepartment(String responsibleDepartment) 
    {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleDepartment() 
    {
        return responsibleDepartment;
    }

    public void setResponsiblePerson(String responsiblePerson) 
    {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePerson() 
    {
        return responsiblePerson;
    }

    public void setPlannedTime(Date plannedTime) 
    {
        this.plannedTime = plannedTime;
    }

    public Date getPlannedTime() 
    {
        return plannedTime;
    }

    public void setLatestStatus(String latestStatus) 
    {
        this.latestStatus = latestStatus;
    }

    public String getLatestStatus() 
    {
        return latestStatus;
    }

    public void setActualTime(Date actualTime) 
    {
        this.actualTime = actualTime;
    }

    public Date getActualTime() 
    {
        return actualTime;
    }

    public void setFeedbackValidators(String feedbackValidators) 
    {
        this.feedbackValidators = feedbackValidators;
    }

    public String getFeedbackValidators() 
    {
        return feedbackValidators;
    }

    public void setBeginDate(Date beginDate) 
    {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() 
    {
        return beginDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getTrialProducer() {
        return trialProducer;
    }

    public void setTrialProducer(String trialProducer) {
        this.trialProducer = trialProducer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("trialid", getTrialid())
            .append("feedbackDepartment", getFeedbackDepartment())
            .append("issueLevel", getIssueLevel())
            .append("causeAnalysis", getCauseAnalysis())
            .append("descriptionProblem", getDescriptionProblem())
            .append("temporarySolution", getTemporarySolution())
            .append("longtermMeasures", getLongtermMeasures())
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("responsiblePerson", getResponsiblePerson())
            .append("plannedTime", getPlannedTime())
            .append("latestStatus", getLatestStatus())
            .append("actualTime", getActualTime())
            .append("feedbackValidators", getFeedbackValidators())
            .append("beginDate", getBeginDate())
            .append("endDate", getEndDate())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
