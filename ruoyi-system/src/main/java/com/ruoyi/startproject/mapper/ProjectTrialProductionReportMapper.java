package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectTrialProductionFinalreport;
import com.ruoyi.startproject.domain.ProjectTrialProductionReport;

import java.util.List;

/**
 * 试制终审报告Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-20
 */
public interface ProjectTrialProductionReportMapper 
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

    /**
     * 查询试制终审报告子
     *
     * @param id 试制终审报告主键
     * @return 试制终审报告子
     */
    public ProjectTrialProductionReport selectProjectTrialProductionFinalreportByMainId(Long id);
    public List<ProjectTrialProductionReport> selectProjectTrialProductionFinalreportByTrialid(Long id);


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
     * 删除试制终审报告
     * 
     * @param id 试制终审报告主键
     * @return 结果
     */
    public int deleteProjectTrialProductionReportById(String id);

    /**
     * 批量删除试制终审报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionReportByIds(String[] ids);
}
