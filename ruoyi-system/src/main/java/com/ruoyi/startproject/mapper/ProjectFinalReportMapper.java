package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectFinalReport;

import java.util.List;


/**
 * 结题报告Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface ProjectFinalReportMapper 
{
    /**
     * 查询结题报告
     * 
     * @param id 结题报告主键
     * @return 结题报告
     */
    public ProjectFinalReport selectProjectFinalReportById(Long id);

    /**
     * 查询结题报告列表
     * 
     * @param projectFinalReport 结题报告
     * @return 结题报告集合
     */
    public List<ProjectFinalReport> selectProjectFinalReportList(ProjectFinalReport projectFinalReport);

    /**
     * 新增结题报告
     * 
     * @param projectFinalReport 结题报告
     * @return 结果
     */
    public int insertProjectFinalReport(ProjectFinalReport projectFinalReport);

    /**
     * 修改结题报告
     * 
     * @param projectFinalReport 结题报告
     * @return 结果
     */
    public int updateProjectFinalReport(ProjectFinalReport projectFinalReport);

    /**
     * 删除结题报告
     * 
     * @param id 结题报告主键
     * @return 结果
     */
    public int deleteProjectFinalReportById(Long id);

    /**
     * 批量删除结题报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectFinalReportByIds(Long[] ids);

    List<ProjectFinalReport> selectProjectFinalReportListByProjectId(Long projectId);
}
