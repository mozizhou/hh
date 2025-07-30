package com.ruoyi.startproject.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 设备主对象 equipment_main
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public class EquipmentMain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 公司名称 */
    @Excel(name = "公司名称")
    private String company;

    /** 月份(1-12) */
    @Excel(name = "月份(1-12)")
    private Long mouth;

    @Excel(name = "年份")
    private Long year;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String equipmentNo;

    /** 地点 */
    @Excel(name = "地点")
    private String place;

    private List<EquipmentUsage> tableData;

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

    public void setMouth(Long mouth) 
    {
        this.mouth = mouth;
    }

    public Long getMouth() 
    {
        return mouth;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setEquipmentNo(String equipmentNo) 
    {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentNo() 
    {
        return equipmentNo;
    }

    public void setPlace(String place) 
    {
        this.place = place;
    }

    public String getPlace() 
    {
        return place;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public List<EquipmentUsage> getTableData() {
        return tableData;
    }

    public void setTableData(List<EquipmentUsage> tableData) {
        this.tableData = tableData;
    }

    public EquipmentMain(Long id, String company, Long mouth, Long year, String equipmentName, String specification, String equipmentNo, String place, List<EquipmentUsage> tableData) {
        this.id = id;
        this.company = company;
        this.mouth = mouth;
        this.year = year;
        this.equipmentName = equipmentName;
        this.specification = specification;
        this.equipmentNo = equipmentNo;
        this.place = place;
        this.tableData = tableData;
    }

    public EquipmentMain(Long id, String company, Long mouth, String equipmentName, String specification, String equipmentNo, String place, List<EquipmentUsage> tableData) {
        this.id = id;
        this.company = company;
        this.mouth = mouth;
        this.equipmentName = equipmentName;
        this.specification = specification;
        this.equipmentNo = equipmentNo;
        this.place = place;
        this.tableData = tableData;
    }

    public EquipmentMain() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("company", getCompany())
            .append("mouth", getMouth())
            .append("equipmentName", getEquipmentName())
            .append("specification", getSpecification())
            .append("equipmentNo", getEquipmentNo())
            .append("place", getPlace())
            .toString();
    }
}
