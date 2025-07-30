package com.ruoyi.startproject.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试对象 project_test
 *
 * @author ruoyi
 * @date 2025-06-17
 */
public class ProjectTest extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String company;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectName;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String projectId;

    private String projectNo;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String testPurpose;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String testConditions;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String equipment;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String procedures;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String operateAndPhenomenonAndData;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String testResult;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String experimenters;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestConditions(String testConditions) {
        this.testConditions = testConditions;
    }

    public String getTestConditions() {
        return testConditions;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getProcedures() {
        return procedures;
    }

    public void setProcedures(String procedures) {
        this.procedures = procedures;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setOperateAndPhenomenonAndData(String operateAndPhenomenonAndData) {
        this.operateAndPhenomenonAndData = operateAndPhenomenonAndData;
    }

    public String getOperateAndPhenomenonAndData() {
        return operateAndPhenomenonAndData;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setExperimenters(String experimenters) {
        this.experimenters = experimenters;
    }

    public String getExperimenters() {
        return experimenters;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public ProjectTest() {
    }

    public ProjectTest(Long id, String company, Date createDate, String projectName, String projectId, String testPurpose, String testConditions, String equipment, String procedures, String operateAndPhenomenonAndData, String testResult, String experimenters, String status) {
        this.id = id;
        this.company = company;
        this.createDate = createDate;
        this.projectName = projectName;
        this.projectId = projectId;
        this.testPurpose = testPurpose;
        this.testConditions = testConditions;
        this.equipment = equipment;
        this.procedures = procedures;
        this.operateAndPhenomenonAndData = operateAndPhenomenonAndData;
        this.testResult = testResult;
        this.experimenters = experimenters;
        this.status = status;
    }

    public ProjectTest(Long id, String company, Date createDate, String projectName, String projectId, String projectNo, String testPurpose, String testConditions, String equipment, String procedures, String operateAndPhenomenonAndData, String testResult, String experimenters, String status) {
        this.id = id;
        this.company = company;
        this.createDate = createDate;
        this.projectName = projectName;
        this.projectId = projectId;
        this.projectNo = projectNo;
        this.testPurpose = testPurpose;
        this.testConditions = testConditions;
        this.equipment = equipment;
        this.procedures = procedures;
        this.operateAndPhenomenonAndData = operateAndPhenomenonAndData;
        this.testResult = testResult;
        this.experimenters = experimenters;
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("company", getCompany())
                .append("createDate", getCreateDate())
                .append("projectName", getProjectName())
                .append("projectId", getProjectId())
                .append("testPurpose", getTestPurpose())
                .append("testConditions", getTestConditions())
                .append("equipment", getEquipment())
                .append("procedure", getProcedures())
                .append("operateAndPhenomenonAndData", getOperateAndPhenomenonAndData())
                .append("testResult", getTestResult())
                .append("experimenters", getExperimenters())
                .append("status", getStatus())
                .toString();
    }
}
