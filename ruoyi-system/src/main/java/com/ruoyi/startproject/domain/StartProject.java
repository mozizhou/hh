package com.ruoyi.startproject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class StartProject {

    private Long Id;
    private String company;
    private Long deptId;
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date createDate;
    private String projectNo;
    private String projectName;
    private String header;

    private List<StartProjectProgress> tableData;

    private String innovation;
    private String marketPosition;
    private String innovationReq;

    private BigDecimal budgetAmount;
    private BigDecimal annualSales;

    private String json;

    // Getters and Setters



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<StartProjectProgress> getTableData() {
        return tableData;
    }

    public void setTableData(List<StartProjectProgress> tableData) {
        this.tableData = tableData;
    }

    public String getInnovation() {
        return innovation;
    }

    public void setInnovation(String innovation) {
        this.innovation = innovation;
    }

    public String getMarketPosition() {
        return marketPosition;
    }

    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }

    public String getInnovationReq() {
        return innovationReq;
    }

    public void setInnovationReq(String innovationReq) {
        this.innovationReq = innovationReq;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getAnnualSales() {
        return annualSales;
    }

    public void setAnnualSales(BigDecimal annualSales) {
        this.annualSales = annualSales;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
