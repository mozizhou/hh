package com.ruoyi.startproject.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目终审对象 final_project_review
 * 
 * @author ruoyi
 * @date 2025-06-10
 */
public class FinalProjectReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String company;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectNo;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 负责人 */
    @Excel(name = "负责人")
    private String header;

    /** 项目简介 */
    @Excel(name = "项目简介")
    private String synopsis;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;
    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 试产可行性分析 */
    @Excel(name = "试产可行性分析")
    private String trialProduction;

    /** 采购可行性分析 */
    @Excel(name = "采购可行性分析")
    private String procurementProduction;

    /** 包材图纸审核意见 */
    @Excel(name = "包材图纸审核意见")
    private String drawingReviewOpinions;

    /** 审核结论 */
    @Excel(name = "审核结论")
    private String conclusions;

    /** 文件路径或存储位置 */
    @Excel(name = "文件路径或存储位置")
    private String file;

    /** 负责人意见 */
    @Excel(name = "负责人意见")
    private String herderOpinion;

    /** 管理层意见 */
    @Excel(name = "管理层意见")
    private String managerOption;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectid;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date createDate;

    /** 项目状态（0:未审核, 1:不通过审核, 2:通过） */
    @Excel(name = "项目状态", readConverterExp = "0=:未审核,,1=:不通过审核,,2=:通过")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }

    public void setProjectNo(String projectNo) 
    {
        this.projectNo = projectNo;
    }

    public String getProjectNo() 
    {
        return projectNo;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setHeader(String header) 
    {
        this.header = header;
    }

    public String getHeader() 
    {
        return header;
    }

    public void setSynopsis(String synopsis) 
    {
        this.synopsis = synopsis;
    }

    public String getSynopsis() 
    {
        return synopsis;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setTrialProduction(String trialProduction) 
    {
        this.trialProduction = trialProduction;
    }

    public String getTrialProduction() 
    {
        return trialProduction;
    }

    public void setProcurementProduction(String procurementProduction) 
    {
        this.procurementProduction = procurementProduction;
    }

    public String getProcurementProduction() 
    {
        return procurementProduction;
    }

    public void setDrawingReviewOpinions(String drawingReviewOpinions) 
    {
        this.drawingReviewOpinions = drawingReviewOpinions;
    }

    public String getDrawingReviewOpinions() 
    {
        return drawingReviewOpinions;
    }

    public void setConclusions(String conclusions) 
    {
        this.conclusions = conclusions;
    }

    public String getConclusions() 
    {
        return conclusions;
    }

    public void setFile(String file) 
    {
        this.file = file;
    }

    public String getFile() 
    {
        return file;
    }

    public void setHerderOpinion(String herderOpinion) 
    {
        this.herderOpinion = herderOpinion;
    }

    public String getHerderOpinion() 
    {
        return herderOpinion;
    }

    public void setManagerOption(String managerOption) 
    {
        this.managerOption = managerOption;
    }

    public String getManagerOption() 
    {
        return managerOption;
    }

    public void setProjectid(Long projectid) 
    {
        this.projectid = projectid;
    }

    public Long getProjectid() 
    {
        return projectid;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public FinalProjectReview(Long id, String company, String projectNo, String projectName, String header, String synopsis, BigDecimal price, String unit, String trialProduction, String procurementProduction, String drawingReviewOpinions, String conclusions, String file, String herderOpinion, String managerOption, Long projectid, Date createDate, Long status) {
        this.id = id;
        this.company = company;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.header = header;
        this.synopsis = synopsis;
        this.price = price;
        this.unit = unit;
        this.trialProduction = trialProduction;
        this.procurementProduction = procurementProduction;
        this.drawingReviewOpinions = drawingReviewOpinions;
        this.conclusions = conclusions;
        this.file = file;
        this.herderOpinion = herderOpinion;
        this.managerOption = managerOption;
        this.projectid = projectid;
        this.createDate = createDate;
        this.status = status;
    }

    public FinalProjectReview() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("company", getCompany())
            .append("projectNo", getProjectNo())
            .append("projectName", getProjectName())
            .append("header", getHeader())
            .append("synopsis", getSynopsis())
            .append("price", getPrice())
            .append("trialProduction", getTrialProduction())
            .append("procurementProduction", getProcurementProduction())
            .append("drawingReviewOpinions", getDrawingReviewOpinions())
            .append("conclusions", getConclusions())
            .append("file", getFile())
            .append("herderOpinion", getHerderOpinion())
            .append("managerOption", getManagerOption())
            .append("projectid", getProjectid())
            .append("status", getStatus())
            .toString();
    }
}
