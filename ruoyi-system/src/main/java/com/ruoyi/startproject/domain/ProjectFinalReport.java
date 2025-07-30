package com.ruoyi.startproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 结题报告对象 project_final_report
 *
 * @author ruoyi
 * @date 2025-06-17
 */
public class ProjectFinalReport extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 项目编号
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectNo;

    /**
     * 项目名称
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectName;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String header;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String deviseCompliance;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String reviewOpinions;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String costCompliance;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String viability;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String conclusions;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String files;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long projectId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String company;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String headerRecommendations;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectHeader;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date projectHeaderDate;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String managerRecommendations;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String manager;

    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date managerDate;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setDeviseCompliance(String deviseCompliance) {
        this.deviseCompliance = deviseCompliance;
    }

    public String getDeviseCompliance() {
        return deviseCompliance;
    }

    public void setReviewOpinions(String reviewOpinions) {
        this.reviewOpinions = reviewOpinions;
    }

    public String getReviewOpinions() {
        return reviewOpinions;
    }

    public void setCostCompliance(String costCompliance) {
        this.costCompliance = costCompliance;
    }

    public String getCostCompliance() {
        return costCompliance;
    }

    public void setViability(String viability) {
        this.viability = viability;
    }

    public String getViability() {
        return viability;
    }

    public void setConclusions(String conclusions) {
        this.conclusions = conclusions;
    }

    public String getConclusions() {
        return conclusions;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFiles() {
        return files;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getHeaderRecommendations() {
        return headerRecommendations;
    }

    public void setHeaderRecommendations(String headerRecommendations) {
        this.headerRecommendations = headerRecommendations;
    }

    public String getProjectHeader() {
        return projectHeader;
    }

    public void setProjectHeader(String projectHeader) {
        this.projectHeader = projectHeader;
    }

    public Date getProjectHeaderDate() {
        return projectHeaderDate;
    }

    public void setProjectHeaderDate(Date projectHeaderDate) {
        this.projectHeaderDate = projectHeaderDate;
    }

    public String getManagerRecommendations() {
        return managerRecommendations;
    }

    public void setManagerRecommendations(String managerRecommendations) {
        this.managerRecommendations = managerRecommendations;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getManagerDate() {
        return managerDate;
    }

    public void setManagerDate(Date managerDate) {
        this.managerDate = managerDate;
    }

    public ProjectFinalReport(Long id, String projectNo, String projectName, String header, String deviseCompliance, String reviewOpinions, String costCompliance, String viability, String conclusions, String files, Long projectId, String company, String headerRecommendations, String projectHeader, Date projectHeaderDate, String managerRecommendations, String manager, Date managerDate) {
        this.id = id;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.header = header;
        this.deviseCompliance = deviseCompliance;
        this.reviewOpinions = reviewOpinions;
        this.costCompliance = costCompliance;
        this.viability = viability;
        this.conclusions = conclusions;
        this.files = files;
        this.projectId = projectId;
        this.company = company;
        this.headerRecommendations = headerRecommendations;
        this.projectHeader = projectHeader;
        this.projectHeaderDate = projectHeaderDate;
        this.managerRecommendations = managerRecommendations;
        this.manager = manager;
        this.managerDate = managerDate;
    }

    public ProjectFinalReport() {

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectNo", getProjectNo())
                .append("projectName", getProjectName())
                .append("header", getHeader())
                .append("deviseCompliance", getDeviseCompliance())
                .append("reviewOpinions", getReviewOpinions())
                .append("costCompliance", getCostCompliance())
                .append("viability", getViability())
                .append("conclusions", getConclusions())
                .append("files", getFiles())
                .append("projectId", getProjectId())
                .append("company", getCompany())
                .append("headerRecommendations", getHeaderRecommendations())
                .append("projectHeader", getProjectHeader())
                .append("projectHeaderDate", getProjectHeaderDate())
                .append("managerRecommendations", getManagerRecommendations())
                .append("manager", getManager())
                .append("managerDate", getManagerDate())
                .toString();
    }
}
