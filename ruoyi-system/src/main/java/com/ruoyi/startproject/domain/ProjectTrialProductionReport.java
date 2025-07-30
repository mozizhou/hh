package com.ruoyi.startproject.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 试制终审报告对象 project_trial_production_report
 * 
 * @author ruoyi
 * @date 2025-06-20
 */
public class ProjectTrialProductionReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    private String company;
    private String quantity;

    private String projectName;
    private Long projectId;
    private String projectNo;
    private String trialProducer;

    /** 试制ID */
    @Excel(name = "试制ID")
    private Long trialid;
    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endtime;

    /** 过程描述 */
    @Excel(name = "过程描述")
    private String process;

    /** 过程概述 */
    @Excel(name = "过程概述")
    private String overviewProcess;

    /** 结果描述 */
    @Excel(name = "结果描述")
    private String result;

    /** 结论与建议 */
    @Excel(name = "结论与建议")
    private String conclusionsAndRecommendations;

    /** 技术负责人 */
    @Excel(name = "技术负责人")
    private String technologyHeader;

    /** 技术负责人日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "技术负责人日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date thDate;

    /** 负责人建议 */
    @Excel(name = "负责人建议")
    private String headerRecommendations;

    /** 项目负责人 */
    @Excel(name = "项目负责人")
    private String projectHeader;

    /** 项目负责人日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目负责人日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date projectHeaderDate;

    /** 管理者建议 */
    @Excel(name = "管理者建议")
    private String managerRecommendations;

    /** 管理者 */
    @Excel(name = "管理者")
    private String manager;

    /** 管理者日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "管理者日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date managerDate;

    /** 记录创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 记录更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setTrialid(Long trialid) 
    {
        this.trialid = trialid;
    }

    public Long getTrialid() 
    {
        return trialid;
    }

    public void setEndtime(Date endtime) 
    {
        this.endtime = endtime;
    }

    public Date getEndtime() 
    {
        return endtime;
    }

    public void setProcess(String process) 
    {
        this.process = process;
    }

    public String getProcess() 
    {
        return process;
    }

    public void setOverviewProcess(String overviewProcess) 
    {
        this.overviewProcess = overviewProcess;
    }

    public String getOverviewProcess() 
    {
        return overviewProcess;
    }

    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }

    public void setConclusionsAndRecommendations(String conclusionsAndRecommendations) 
    {
        this.conclusionsAndRecommendations = conclusionsAndRecommendations;
    }

    public String getConclusionsAndRecommendations() 
    {
        return conclusionsAndRecommendations;
    }

    public void setTechnologyHeader(String technologyHeader) 
    {
        this.technologyHeader = technologyHeader;
    }

    public String getTechnologyHeader() 
    {
        return technologyHeader;
    }

    public void setThDate(Date thDate) 
    {
        this.thDate = thDate;
    }

    public Date getThDate() 
    {
        return thDate;
    }

    public void setHeaderRecommendations(String headerRecommendations) 
    {
        this.headerRecommendations = headerRecommendations;
    }

    public String getHeaderRecommendations() 
    {
        return headerRecommendations;
    }

    public void setProjectHeader(String projectHeader) 
    {
        this.projectHeader = projectHeader;
    }

    public String getProjectHeader() 
    {
        return projectHeader;
    }

    public void setProjectHeaderDate(Date projectHeaderDate) 
    {
        this.projectHeaderDate = projectHeaderDate;
    }

    public Date getProjectHeaderDate() 
    {
        return projectHeaderDate;
    }

    public void setManagerRecommendations(String managerRecommendations) 
    {
        this.managerRecommendations = managerRecommendations;
    }

    public String getManagerRecommendations() 
    {
        return managerRecommendations;
    }

    public void setManager(String manager) 
    {
        this.manager = manager;
    }

    public String getManager() 
    {
        return manager;
    }

    public void setManagerDate(Date managerDate) 
    {
        this.managerDate = managerDate;
    }

    public Date getManagerDate() 
    {
        return managerDate;
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


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public ProjectTrialProductionReport() {
    }


    public ProjectTrialProductionReport(Long id, String status, String company, String quantity, String projectName, Long projectId, String projectNo, String trialProducer, Long trialid, Date createtime, Date endtime, String process, String overviewProcess, String result, String conclusionsAndRecommendations, String technologyHeader, Date thDate, String headerRecommendations, String projectHeader, Date projectHeaderDate, String managerRecommendations, String manager, Date managerDate, Date createdAt, Date updatedAt) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.quantity = quantity;
        this.projectName = projectName;
        this.projectId = projectId;
        this.projectNo = projectNo;
        this.trialProducer = trialProducer;
        this.trialid = trialid;
        this.createtime = createtime;
        this.endtime = endtime;
        this.process = process;
        this.overviewProcess = overviewProcess;
        this.result = result;
        this.conclusionsAndRecommendations = conclusionsAndRecommendations;
        this.technologyHeader = technologyHeader;
        this.thDate = thDate;
        this.headerRecommendations = headerRecommendations;
        this.projectHeader = projectHeader;
        this.projectHeaderDate = projectHeaderDate;
        this.managerRecommendations = managerRecommendations;
        this.manager = manager;
        this.managerDate = managerDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("trialid", getTrialid())
            .append("createtime", getCreatetime())
            .append("endtime", getEndtime())
            .append("process", getProcess())
            .append("overviewProcess", getOverviewProcess())
            .append("result", getResult())
            .append("conclusionsAndRecommendations", getConclusionsAndRecommendations())
            .append("technologyHeader", getTechnologyHeader())
            .append("thDate", getThDate())
            .append("headerRecommendations", getHeaderRecommendations())
            .append("projectHeader", getProjectHeader())
            .append("projectHeaderDate", getProjectHeaderDate())
            .append("managerRecommendations", getManagerRecommendations())
            .append("manager", getManager())
            .append("managerDate", getManagerDate())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
