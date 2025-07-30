package com.ruoyi.startproject.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品试用反馈对象 project_trial_feedback
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public class ProjectTrialFeedback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long mainid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long index;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long feedid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "GMT+8")
    private Date feedDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String productPerformance;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String improvements;
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String solution;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMainid(Long mainid) 
    {
        this.mainid = mainid;
    }

    public Long getMainid() 
    {
        return mainid;
    }

    public void setIndex(Long index) 
    {
        this.index = index;
    }

    public Long getIndex() 
    {
        return index;
    }

    public void setFeedid(Long feedid) 
    {
        this.feedid = feedid;
    }

    public Long getFeedid() 
    {
        return feedid;
    }

    public Date getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(Date feedDate) {
        this.feedDate = feedDate;
    }

    public void setProductPerformance(String productPerformance)
    {
        this.productPerformance = productPerformance;
    }

    public String getProductPerformance() 
    {
        return productPerformance;
    }

    public void setImprovements(String improvements) 
    {
        this.improvements = improvements;
    }

    public String getImprovements() 
    {
        return improvements;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public ProjectTrialFeedback(Long id, Long mainid, Long index, Long feedid, Date feedDate, String productPerformance, String improvements, String solution) {
        this.id = id;
        this.mainid = mainid;
        this.index = index;
        this.feedid = feedid;
        this.feedDate = feedDate;
        this.productPerformance = productPerformance;
        this.improvements = improvements;
        this.solution = solution;
    }

    public ProjectTrialFeedback() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainid", getMainid())
            .append("index", getIndex())
            .append("feedid", getFeedid())
            .append("feedDate", getFeedDate())
            .append("productPerformance", getProductPerformance())
            .append("improvements", getImprovements())
            .toString();
    }
}
