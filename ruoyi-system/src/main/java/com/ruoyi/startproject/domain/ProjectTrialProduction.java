package com.ruoyi.startproject.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 试制与问题跟踪主对象 project_trial_production
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public class ProjectTrialProduction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    @Excel(name = "项目编号")
    private String projectNo;

    /** 公司名称 */
    @Excel(name = "公司名称")

    private String company;

    /** 数量 */
    @Excel(name = "数量")
    private String quantity;

    /** 设备信息 */
    @Excel(name = "设备信息")
    private String equipment;

    /** 原型状态 */
    @Excel(name = "原型状态")
    private String prototypeStatus;

    /** 试产负责人 */
    @Excel(name = "试产负责人")
    private String trialProducer;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 文件编号 */
    @Excel(name = "文件编号")
    private String fileNo;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 填写人 */
    @Excel(name = "填写人")
    private String fillPeople;

    /** 审核人 */
    @Excel(name = "审核人")
    private String reviewPeople;
    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;
    //是否有问题跟踪表
    private Boolean istableData;

    private List<ProjectTrialProductionTableData> tableData;

    private ProjectTrialProductionReport finalReviewForm;



    public ProjectTrialProductionReport getFinalReviewForm() {
        return finalReviewForm;
    }

    public void setFinalReviewForm(ProjectTrialProductionReport finalReviewForm) {
        this.finalReviewForm = finalReviewForm;
    }

    public Boolean getIstableData() {
        return istableData;
    }

    public void setIstableData(Boolean istableData) {
        this.istableData = istableData;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

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

    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }

    public void setQuantity(String quantity) 
    {
        this.quantity = quantity;
    }

    public String getQuantity() 
    {
        return quantity;
    }

    public void setEquipment(String equipment) 
    {
        this.equipment = equipment;
    }

    public String getEquipment() 
    {
        return equipment;
    }

    public void setPrototypeStatus(String prototypeStatus) 
    {
        this.prototypeStatus = prototypeStatus;
    }

    public String getPrototypeStatus() 
    {
        return prototypeStatus;
    }

    public void setTrialProducer(String trialProducer) 
    {
        this.trialProducer = trialProducer;
    }

    public String getTrialProducer() 
    {
        return trialProducer;
    }

    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setFileNo(String fileNo) 
    {
        this.fileNo = fileNo;
    }

    public String getFileNo() 
    {
        return fileNo;
    }

    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }

    public void setFillPeople(String fillPeople) 
    {
        this.fillPeople = fillPeople;
    }

    public String getFillPeople() 
    {
        return fillPeople;
    }

    public void setReviewPeople(String reviewPeople) 
    {
        this.reviewPeople = reviewPeople;
    }

    public String getReviewPeople() 
    {
        return reviewPeople;
    }

    public List<ProjectTrialProductionTableData> getTableData() {
        return tableData;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setTableData(List<ProjectTrialProductionTableData> tableData) {
        this.tableData = tableData;
    }


    public ProjectTrialProduction() {
    }

    public ProjectTrialProduction(Long id, String status, String projectNo, String company, String quantity, String equipment, String prototypeStatus, String trialProducer, Date createDate, String projectName, Long projectId, String fileNo, String department, String fillPeople, String reviewPeople, String unit, Boolean istableData, List<ProjectTrialProductionTableData> tableData, ProjectTrialProductionReport finalReviewForm) {
        this.id = id;
        this.status = status;
        this.projectNo = projectNo;
        this.company = company;
        this.quantity = quantity;
        this.equipment = equipment;
        this.prototypeStatus = prototypeStatus;
        this.trialProducer = trialProducer;
        this.createDate = createDate;
        this.projectName = projectName;
        this.projectId = projectId;
        this.fileNo = fileNo;
        this.department = department;
        this.fillPeople = fillPeople;
        this.reviewPeople = reviewPeople;
        this.unit = unit;
        this.istableData = istableData;
        this.tableData = tableData;
        this.finalReviewForm = finalReviewForm;
    }

    public ProjectTrialProduction(Long id, String status, String projectNp, String company, String quantity, String equipment, String prototypeStatus, String trialProducer, Date createDate, String projectName, Long projectId, String fileNo, String department, String fillPeople, String reviewPeople, String unit, List<ProjectTrialProductionTableData> tableData, ProjectTrialProductionReport finalReviewForm) {
        this.id = id;
        this.status = status;
        this.company = company;
        this.projectNo = projectNp;
        this.quantity = quantity;
        this.equipment = equipment;
        this.prototypeStatus = prototypeStatus;
        this.trialProducer = trialProducer;
        this.createDate = createDate;
        this.projectName = projectName;
        this.projectId = projectId;
        this.fileNo = fileNo;
        this.department = department;
        this.fillPeople = fillPeople;
        this.reviewPeople = reviewPeople;
        this.unit = unit;
        this.tableData = tableData;
        this.finalReviewForm = finalReviewForm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("status", getStatus())
            .append("projectNo", getProjectNo())
            .append("company", getCompany())
            .append("quantity", getQuantity())
            .append("equipment", getEquipment())
            .append("prototypeStatus", getPrototypeStatus())
            .append("trialProducer", getTrialProducer())
            .append("createDate", getCreateDate())
            .append("projectName", getProjectName())
            .append("fileNo", getFileNo())
            .append("department", getDepartment())
            .append("fillPeople", getFillPeople())
            .append("reviewPeople", getReviewPeople())
            .append("tableData", getTableData())
            .toString();
    }
}
