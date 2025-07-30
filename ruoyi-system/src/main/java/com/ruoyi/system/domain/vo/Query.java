package com.ruoyi.system.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Query extends BaseEntity {
    private Long Id;
    private String company;
    private Long deptId;
    private Date createDate;
    private String projectNo;
    private String projectName;
    private String header;

    private String innovation;
    private String marketPosition;
    private String innovationReq;

    private BigDecimal budgetAmount;
    private BigDecimal annualSales;

    public Query(Long id, String company, Long deptId, Date createDate, String projectNo, String projectName, String header, String innovation, String marketPosition, String innovationReq, BigDecimal budgetAmount, BigDecimal annualSales) {
        Id = id;
        this.company = company;
        this.deptId = deptId;
        this.createDate = createDate;
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.header = header;
        this.innovation = innovation;
        this.marketPosition = marketPosition;
        this.innovationReq = innovationReq;
        this.budgetAmount = budgetAmount;
        this.annualSales = annualSales;
    }

    public Query() {
    }

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
}
