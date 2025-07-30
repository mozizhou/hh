package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionReport;

import java.util.List;


/**
 * 试制终审报告Service接口
 * 
 * @author ruoyi
 * @date 2025-06-20
 */
public interface IProjectTrialProductionReportService 
{
    /**
     * 查询试制终审报告
     * 
     * @param id 试制终审报告主键
     * @return 试制终审报告
     */
    public ProjectTrialProductionReport selectProjectTrialProductionReportById(String id);

    /**
     * 查询试制终审报告列表
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 试制终审报告集合
     */
    public List<ProjectTrialProductionReport> selectProjectTrialProductionReportList(ProjectTrialProductionReport projectTrialProductionReport);


    public ProjectTrialProductionReport selectProjectTrialProductionFinalreportByMainId(ProjectTrialProduction projectTrialProduction);

    /**
     * 新增试制终审报告
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 结果
     */
    public int insertProjectTrialProductionReport(ProjectTrialProductionReport projectTrialProductionReport);

    /**
     * 修改试制终审报告
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 结果
     */
    public int updateProjectTrialProductionReport(ProjectTrialProductionReport projectTrialProductionReport);

    /**
     * 批量删除试制终审报告
     * 
     * @param ids 需要删除的试制终审报告主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionReportByIds(String[] ids);

    /**
     * 删除试制终审报告信息
     * 
     * @param id 试制终审报告主键
     * @return 结果
     */
    public int deleteProjectTrialProductionReportById(String id);
}
