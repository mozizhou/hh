package com.ruoyi.startproject.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 研发人员工时汇总主对象 rd_emp_hours_main
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public class RdEmpHoursMain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主表唯一ID */
    private String id;

    /** 工作年份（如：2025） */
    @Excel(name = "工作年份", readConverterExp = "如=：2025")
    private Long workYear;

    /** 工作月份（1~12） */
    @Excel(name = "工作月份", readConverterExp = "1=~12")
    private Long workMonth;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 研发人员姓名 */
    @Excel(name = "研发人员姓名")
    private String empName;

    /** 月工作总时间（小时） */
    @Excel(name = "月工作总时间", readConverterExp = "小=时")
    private BigDecimal monthWorkHours;

    /** 项目维度工作时间（小时） */
    @Excel(name = "项目维度工作时间", readConverterExp = "小=时")
    private BigDecimal projectWorkHours;

    /** 从事岗位 */
    @Excel(name = "从事岗位")
    private String jobPost;

    /** 人员类型 */
    @Excel(name = "人员类型")
    private String empType;

    /** 公司 */
    @Excel(name = "公司")
    private String company;

    private Boolean hasChildren;

    /** 1号当日工时 */
    @Excel(name = "1号当日工时")
    private BigDecimal day1;

    /** 2号当日工时 */
    @Excel(name = "2号当日工时")
    private BigDecimal day2;

    /** 3号当日工时 */
    @Excel(name = "3号当日工时")
    private BigDecimal day3;

    /** 4号当日工时 */
    @Excel(name = "4号当日工时")
    private BigDecimal day4;

    /** 5号当日工时 */
    @Excel(name = "5号当日工时")
    private BigDecimal day5;

    /** 6号当日工时 */
    @Excel(name = "6号当日工时")
    private BigDecimal day6;

    /** 7号当日工时 */
    @Excel(name = "7号当日工时")
    private BigDecimal day7;

    /** 8号当日工时 */
    @Excel(name = "8号当日工时")
    private BigDecimal day8;

    /** 9号当日工时 */
    @Excel(name = "9号当日工时")
    private BigDecimal day9;

    /** 10号当日工时 */
    @Excel(name = "10号当日工时")
    private BigDecimal day10;

    /** 11号当日工时 */
    @Excel(name = "11号当日工时")
    private BigDecimal day11;

    /** 12号当日工时 */
    @Excel(name = "12号当日工时")
    private BigDecimal day12;

    /** 13号当日工时 */
    @Excel(name = "13号当日工时")
    private BigDecimal day13;

    /** 14号当日工时 */
    @Excel(name = "14号当日工时")
    private BigDecimal day14;

    /** 15号当日工时 */
    @Excel(name = "15号当日工时")
    private BigDecimal day15;

    /** 16号当日工时 */
    @Excel(name = "16号当日工时")
    private BigDecimal day16;

    /** 17号当日工时 */
    @Excel(name = "17号当日工时")
    private BigDecimal day17;

    /** 18号当日工时 */
    @Excel(name = "18号当日工时")
    private BigDecimal day18;

    /** 19号当日工时 */
    @Excel(name = "19号当日工时")
    private BigDecimal day19;

    /** 20号当日工时 */
    @Excel(name = "20号当日工时")
    private BigDecimal day20;

    /** 21号当日工时 */
    @Excel(name = "21号当日工时")
    private BigDecimal day21;

    /** 22号当日工时 */
    @Excel(name = "22号当日工时")
    private BigDecimal day22;

    /** 23号当日工时 */
    @Excel(name = "23号当日工时")
    private BigDecimal day23;

    /** 24号当日工时 */
    @Excel(name = "24号当日工时")
    private BigDecimal day24;

    /** 25号当日工时 */
    @Excel(name = "25号当日工时")
    private BigDecimal day25;

    /** 26号当日工时 */
    @Excel(name = "26号当日工时")
    private BigDecimal day26;

    /** 27号当日工时 */
    @Excel(name = "27号当日工时")
    private BigDecimal day27;

    /** 28号当日工时 */
    @Excel(name = "28号当日工时")
    private BigDecimal day28;

    /** 29号当日工时 */
    @Excel(name = "29号当日工时")
    private BigDecimal day29;

    /** 30号当日工时 */
    @Excel(name = "30号当日工时")
    private BigDecimal day30;

    /** 31号当日工时 */
    @Excel(name = "31号当日工时")
    private BigDecimal day31;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    public void setWorkYear(Long workYear) 
    {
        this.workYear = workYear;
    }

    public Long getWorkYear() 
    {
        return workYear;
    }

    public void setWorkMonth(Long workMonth) 
    {
        this.workMonth = workMonth;
    }

    public Long getWorkMonth() 
    {
        return workMonth;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setEmpName(String empName) 
    {
        this.empName = empName;
    }

    public String getEmpName() 
    {
        return empName;
    }

    public void setMonthWorkHours(BigDecimal monthWorkHours) 
    {
        this.monthWorkHours = monthWorkHours;
    }

    public BigDecimal getMonthWorkHours() 
    {
        return monthWorkHours;
    }

    public void setProjectWorkHours(BigDecimal projectWorkHours) 
    {
        this.projectWorkHours = projectWorkHours;
    }

    public BigDecimal getProjectWorkHours() 
    {
        return projectWorkHours;
    }

    public void setJobPost(String jobPost) 
    {
        this.jobPost = jobPost;
    }

    public String getJobPost() 
    {
        return jobPost;
    }

    public void setEmpType(String empType) 
    {
        this.empType = empType;
    }

    public String getEmpType() 
    {
        return empType;
    }

    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }

    public void setDay1(BigDecimal day1) 
    {
        this.day1 = day1;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public BigDecimal getDay1()
    {
        return day1;
    }

    public void setDay2(BigDecimal day2) 
    {
        this.day2 = day2;
    }

    public BigDecimal getDay2() 
    {
        return day2;
    }

    public void setDay3(BigDecimal day3) 
    {
        this.day3 = day3;
    }

    public BigDecimal getDay3() 
    {
        return day3;
    }

    public void setDay4(BigDecimal day4) 
    {
        this.day4 = day4;
    }

    public BigDecimal getDay4() 
    {
        return day4;
    }

    public void setDay5(BigDecimal day5) 
    {
        this.day5 = day5;
    }

    public BigDecimal getDay5() 
    {
        return day5;
    }

    public void setDay6(BigDecimal day6) 
    {
        this.day6 = day6;
    }

    public BigDecimal getDay6() 
    {
        return day6;
    }

    public void setDay7(BigDecimal day7) 
    {
        this.day7 = day7;
    }

    public BigDecimal getDay7() 
    {
        return day7;
    }

    public void setDay8(BigDecimal day8) 
    {
        this.day8 = day8;
    }

    public BigDecimal getDay8() 
    {
        return day8;
    }

    public void setDay9(BigDecimal day9) 
    {
        this.day9 = day9;
    }

    public BigDecimal getDay9() 
    {
        return day9;
    }

    public void setDay10(BigDecimal day10) 
    {
        this.day10 = day10;
    }

    public BigDecimal getDay10() 
    {
        return day10;
    }

    public void setDay11(BigDecimal day11) 
    {
        this.day11 = day11;
    }

    public BigDecimal getDay11() 
    {
        return day11;
    }

    public void setDay12(BigDecimal day12) 
    {
        this.day12 = day12;
    }

    public BigDecimal getDay12() 
    {
        return day12;
    }

    public void setDay13(BigDecimal day13) 
    {
        this.day13 = day13;
    }

    public BigDecimal getDay13() 
    {
        return day13;
    }

    public void setDay14(BigDecimal day14) 
    {
        this.day14 = day14;
    }

    public BigDecimal getDay14() 
    {
        return day14;
    }

    public void setDay15(BigDecimal day15) 
    {
        this.day15 = day15;
    }

    public BigDecimal getDay15() 
    {
        return day15;
    }

    public void setDay16(BigDecimal day16) 
    {
        this.day16 = day16;
    }

    public BigDecimal getDay16() 
    {
        return day16;
    }

    public void setDay17(BigDecimal day17) 
    {
        this.day17 = day17;
    }

    public BigDecimal getDay17() 
    {
        return day17;
    }

    public void setDay18(BigDecimal day18) 
    {
        this.day18 = day18;
    }

    public BigDecimal getDay18() 
    {
        return day18;
    }

    public void setDay19(BigDecimal day19) 
    {
        this.day19 = day19;
    }

    public BigDecimal getDay19() 
    {
        return day19;
    }

    public void setDay20(BigDecimal day20) 
    {
        this.day20 = day20;
    }

    public BigDecimal getDay20() 
    {
        return day20;
    }

    public void setDay21(BigDecimal day21) 
    {
        this.day21 = day21;
    }

    public BigDecimal getDay21() 
    {
        return day21;
    }

    public void setDay22(BigDecimal day22) 
    {
        this.day22 = day22;
    }

    public BigDecimal getDay22() 
    {
        return day22;
    }

    public void setDay23(BigDecimal day23) 
    {
        this.day23 = day23;
    }

    public BigDecimal getDay23() 
    {
        return day23;
    }

    public void setDay24(BigDecimal day24) 
    {
        this.day24 = day24;
    }

    public BigDecimal getDay24() 
    {
        return day24;
    }

    public void setDay25(BigDecimal day25) 
    {
        this.day25 = day25;
    }

    public BigDecimal getDay25() 
    {
        return day25;
    }

    public void setDay26(BigDecimal day26) 
    {
        this.day26 = day26;
    }

    public BigDecimal getDay26() 
    {
        return day26;
    }

    public void setDay27(BigDecimal day27) 
    {
        this.day27 = day27;
    }

    public BigDecimal getDay27() 
    {
        return day27;
    }

    public void setDay28(BigDecimal day28) 
    {
        this.day28 = day28;
    }

    public BigDecimal getDay28() 
    {
        return day28;
    }

    public void setDay29(BigDecimal day29) 
    {
        this.day29 = day29;
    }

    public BigDecimal getDay29() 
    {
        return day29;
    }

    public void setDay30(BigDecimal day30) 
    {
        this.day30 = day30;
    }

    public BigDecimal getDay30() 
    {
        return day30;
    }

    public void setDay31(BigDecimal day31) 
    {
        this.day31 = day31;
    }

    public BigDecimal getDay31() 
    {
        return day31;
    }

    public RdEmpHoursMain(String id, Long workYear, Long workMonth, String projectName, String empName, BigDecimal monthWorkHours, BigDecimal projectWorkHours, String jobPost, String empType, String company, Boolean hasChildren, BigDecimal day1, BigDecimal day2, BigDecimal day3, BigDecimal day4, BigDecimal day5, BigDecimal day6, BigDecimal day7, BigDecimal day8, BigDecimal day9, BigDecimal day10, BigDecimal day11, BigDecimal day12, BigDecimal day13, BigDecimal day14, BigDecimal day15, BigDecimal day16, BigDecimal day17, BigDecimal day18, BigDecimal day19, BigDecimal day20, BigDecimal day21, BigDecimal day22, BigDecimal day23, BigDecimal day24, BigDecimal day25, BigDecimal day26, BigDecimal day27, BigDecimal day28, BigDecimal day29, BigDecimal day30, BigDecimal day31) {
        this.id = id;
        this.workYear = workYear;
        this.workMonth = workMonth;
        this.projectName = projectName;
        this.empName = empName;
        this.monthWorkHours = monthWorkHours;
        this.projectWorkHours = projectWorkHours;
        this.jobPost = jobPost;
        this.empType = empType;
        this.company = company;
        this.hasChildren = hasChildren;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
        this.day8 = day8;
        this.day9 = day9;
        this.day10 = day10;
        this.day11 = day11;
        this.day12 = day12;
        this.day13 = day13;
        this.day14 = day14;
        this.day15 = day15;
        this.day16 = day16;
        this.day17 = day17;
        this.day18 = day18;
        this.day19 = day19;
        this.day20 = day20;
        this.day21 = day21;
        this.day22 = day22;
        this.day23 = day23;
        this.day24 = day24;
        this.day25 = day25;
        this.day26 = day26;
        this.day27 = day27;
        this.day28 = day28;
        this.day29 = day29;
        this.day30 = day30;
        this.day31 = day31;
    }

    public RdEmpHoursMain() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workYear", getWorkYear())
            .append("workMonth", getWorkMonth())
            .append("projectName", getProjectName())
            .append("empName", getEmpName())
            .append("monthWorkHours", getMonthWorkHours())
            .append("projectWorkHours", getProjectWorkHours())
            .append("jobPost", getJobPost())
            .append("empType", getEmpType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("company", getCompany())
            .append("hasChildren", getHasChildren())
            .append("day1", getDay1())
            .append("day2", getDay2())
            .append("day3", getDay3())
            .append("day4", getDay4())
            .append("day5", getDay5())
            .append("day6", getDay6())
            .append("day7", getDay7())
            .append("day8", getDay8())
            .append("day9", getDay9())
            .append("day10", getDay10())
            .append("day11", getDay11())
            .append("day12", getDay12())
            .append("day13", getDay13())
            .append("day14", getDay14())
            .append("day15", getDay15())
            .append("day16", getDay16())
            .append("day17", getDay17())
            .append("day18", getDay18())
            .append("day19", getDay19())
            .append("day20", getDay20())
            .append("day21", getDay21())
            .append("day22", getDay22())
            .append("day23", getDay23())
            .append("day24", getDay24())
            .append("day25", getDay25())
            .append("day26", getDay26())
            .append("day27", getDay27())
            .append("day28", getDay28())
            .append("day29", getDay29())
            .append("day30", getDay30())
            .append("day31", getDay31())
            .toString();
    }
}
