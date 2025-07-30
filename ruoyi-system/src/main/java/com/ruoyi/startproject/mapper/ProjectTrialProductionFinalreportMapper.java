package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectTrialProductionFinalreport;

import java.util.List;


/**
 * 试制终审报告子Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public interface ProjectTrialProductionFinalreportMapper 
{
    /**
     * 查询试制终审报告子
     * 
     * @param id 试制终审报告子主键
     * @return 试制终审报告子
     */
    public ProjectTrialProductionFinalreport selectProjectTrialProductionFinalreportById(Long id);

    /**
     * 查询试制终审报告子
     *
     * @param id 试制终审报告主键
     * @return 试制终审报告子
     */
    public ProjectTrialProductionFinalreport selectProjectTrialProductionFinalreportByMainId(Long id);

    /**
     * 查询试制终审报告子列表
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 试制终审报告子集合
     */
    public List<ProjectTrialProductionFinalreport> selectProjectTrialProductionFinalreportList(ProjectTrialProductionFinalreport projectTrialProductionFinalreport);

    /**
     * 新增试制终审报告子
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 结果
     */
    public int insertProjectTrialProductionFinalreport(ProjectTrialProductionFinalreport projectTrialProductionFinalreport);

    /**
     * 修改试制终审报告子
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 结果
     */
    public int updateProjectTrialProductionFinalreport(ProjectTrialProductionFinalreport projectTrialProductionFinalreport);

    /**
     * 删除试制终审报告子
     * 
     * @param id 试制终审报告子主键
     * @return 结果
     */
    public int deleteProjectTrialProductionFinalreportById(Long id);

    /**
     * 批量删除试制终审报告子
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionFinalreportByIds(Long[] ids);
}
