package com.ruoyi.startproject.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备使用记录对象 equipment_usage
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public class EquipmentUsage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 关联主表ID */
    @Excel(name = "关联主表ID")
    private Long mainId;

    /** 日期(1-31) */
    @Excel(name = "日期(1-31)")
    private Long day;

    /** 上午开始时间 */
    @Excel(name = "上午开始时间", dateFormat = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTimeAm;

    /** 上午结束时间 */
    @Excel(name = "上午结束时间", dateFormat = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date stopTimeAm;

    /** 上午实际时长(小时) */
    @Excel(name = "上午实际时长(小时)")
    private Float actualTimeAm;

    /** 下午开始时间 */
    @Excel(name = "下午开始时间", dateFormat = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTimePm;

    /** 下午结束时间 */
    @Excel(name = "下午结束时间", dateFormat = "HH:mm:ss")
    @JsonFormat(pattern = "HH:mm:ss")
    private Date stopTimePm;

    /** 下午实际时长(小时) */
    @Excel(name = "下午实际时长(小时)")
    private Float actualTimePm;

    /** 运行情况 */
    @Excel(name = "运行情况")
    private String operation;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String department;

    /** 使用人 */
    @Excel(name = "使用人")
    private String users;

    /** 备注 */
    @Excel(name = "备注")
    private String mark;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMainId(Long mainId) 
    {
        this.mainId = mainId;
    }

    public Long getMainId() 
    {
        return mainId;
    }

    public void setDay(Long day) 
    {
        this.day = day;
    }

    public Long getDay() 
    {
        return day;
    }

    public void setStartTimeAm(Date startTimeAm) 
    {
        this.startTimeAm = startTimeAm;
    }

    public Date getStartTimeAm() 
    {
        return startTimeAm;
    }

    public void setStopTimeAm(Date stopTimeAm) 
    {
        this.stopTimeAm = stopTimeAm;
    }

    public Date getStopTimeAm() 
    {
        return stopTimeAm;
    }

    public Float getActualTimeAm() {
        return actualTimeAm;
    }

    public void setActualTimeAm(Float actualTimeAm) {
        this.actualTimeAm = actualTimeAm;
    }

    public Float getActualTimePm() {
        return actualTimePm;
    }

    public void setActualTimePm(Float actualTimePm) {
        this.actualTimePm = actualTimePm;
    }

    public void setStartTimePm(Date startTimePm)
    {
        this.startTimePm = startTimePm;
    }

    public Date getStartTimePm() 
    {
        return startTimePm;
    }

    public void setStopTimePm(Date stopTimePm) 
    {
        this.stopTimePm = stopTimePm;
    }

    public Date getStopTimePm() 
    {
        return stopTimePm;
    }



    public void setOperation(String operation) 
    {
        this.operation = operation;
    }

    public String getOperation() 
    {
        return operation;
    }

    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }

    public void setUsers(String users) 
    {
        this.users = users;
    }

    public String getUsers() 
    {
        return users;
    }

    public void setMark(String mark) 
    {
        this.mark = mark;
    }

    public String getMark() 
    {
        return mark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mainId", getMainId())
            .append("day", getDay())
            .append("startTimeAm", getStartTimeAm())
            .append("stopTimeAm", getStopTimeAm())
            .append("actualTimeAm", getActualTimeAm())
            .append("startTimePm", getStartTimePm())
            .append("stopTimePm", getStopTimePm())
            .append("actualTimePm", getActualTimePm())
            .append("operation", getOperation())
            .append("department", getDepartment())
            .append("users", getUsers())
            .append("mark", getMark())
            .toString();
    }
}
