package com.ruoyi.startproject.domain;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 材料领用对象 material_summary
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public class MaterialSummary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 公司名称 */
    private String company;

    /** 年份(YYYY) */
    @Excel(name = "年份(YYYY)")
    private String year;

    /** 月份(MM) */
    @Excel(name = "月份(MM)")
    private String mouth;

    @Excel(name = "日期")
    private String days;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 存货名称 */
    @Excel(name = "存货名称")
    private String name;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 总数量（允许负值） */
    @Excel(name = "数量")
    private BigDecimal total;

    @Excel(name = "单价")
    private BigDecimal price;

    /** 总金额（允许负值） */
    @Excel(name = "金额")
    private BigDecimal totalAmount;

    /** 选择人 */
    @Excel(name = "领料人")
    private String picker;

    private Boolean hasChildren;

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

    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }

    public void setMouth(String mouth) 
    {
        this.mouth = mouth;
    }

    public String getMouth() 
    {
        return mouth;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setPicker(String picker) 
    {
        this.picker = picker;
    }

    public String getPicker() 
    {
        return picker;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

//    public void setTotal(Long total)
//    {
//        this.total = total!= null ? total : 0L;
//    }
//
//    public Long getTotal()
//    {
//        return total;
//    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public MaterialSummary(Long id, String company, String year, String mouth, String days, String projectName, String name, String unit, BigDecimal total, BigDecimal price, BigDecimal totalAmount, String picker, Boolean hasChildren) {
        this.id = id;
        this.company = company;
        this.year = year;
        this.mouth = mouth;
        this.days = days;
        this.projectName = projectName;
        this.name = name;
        this.unit = unit;
        this.total = total;
        this.price = price;
        this.totalAmount = totalAmount;
        this.picker = picker;
        this.hasChildren = hasChildren;
    }

    public MaterialSummary(Long id, String company, String year, String mouth, String projectName, String name, String unit, BigDecimal total, BigDecimal price, BigDecimal totalAmount, String picker, Boolean hasChildren) {
        this.id = id;
        this.company = company;
        this.year = year;
        this.mouth = mouth;
        this.projectName = projectName;
        this.name = name;
        this.unit = unit;
        this.total = total;
        this.price = price;
        this.totalAmount = totalAmount;
        this.picker = picker;
        this.hasChildren = hasChildren;
    }
    public MaterialSummary(Long id, String company, String year, String mouth, String projectName, String name, String unit, BigDecimal total, BigDecimal totalAmount, String picker, Boolean hasChildren) {
        this.id = id;
        this.company = company;
        this.year = year;
        this.mouth = mouth;
        this.projectName = projectName;
        this.name = name;
        this.unit = unit;
        this.total = total;
        this.totalAmount = totalAmount;
        this.picker = picker;
        this.hasChildren = hasChildren;
    }

    public MaterialSummary() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("company", getCompany())
            .append("year", getYear())
            .append("mouth", getMouth())
            .append("projectName", getProjectName())
            .append("picker", getPicker())
            .append("name", getName())
            .append("unit", getUnit())
            .append("total", getTotal())
            .append("totalAmount", getTotalAmount())
            .toString();
    }
}
