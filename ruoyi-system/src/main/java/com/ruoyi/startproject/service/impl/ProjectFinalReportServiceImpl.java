package com.ruoyi.startproject.service.impl;


import com.ruoyi.startproject.domain.ProjectFinalReport;
import com.ruoyi.startproject.mapper.ProjectFinalReportMapper;
import com.ruoyi.startproject.service.IProjectFinalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结题报告Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class ProjectFinalReportServiceImpl implements IProjectFinalReportService
{
    @Autowired
    private ProjectFinalReportMapper projectFinalReportMapper;

    /**
     * 查询结题报告
     * 
     * @param id 结题报告主键
     * @return 结题报告
     */
    @Override
    public ProjectFinalReport selectProjectFinalReportById(Long id)
    {
        return projectFinalReportMapper.selectProjectFinalReportById(id);
    }

    /**
     * 查询结题报告列表
     * 
     * @param projectFinalReport 结题报告
     * @return 结题报告
     */
    @Override
    public List<ProjectFinalReport> selectProjectFinalReportList(ProjectFinalReport projectFinalReport)
    {
        return projectFinalReportMapper.selectProjectFinalReportList(projectFinalReport);
    }

    /**
     * 新增结题报告
     * 
     * @param projectFinalReport 结题报告
     * @return 结果
     */
    @Override
    public int insertProjectFinalReport(ProjectFinalReport projectFinalReport)
    {
        return projectFinalReportMapper.insertProjectFinalReport(projectFinalReport);
    }

    /**
     * 修改结题报告
     * 
     * @param projectFinalReport 结题报告
     * @return 结果
     */
    @Override
    public int updateProjectFinalReport(ProjectFinalReport projectFinalReport)
    {
        return projectFinalReportMapper.updateProjectFinalReport(projectFinalReport);
    }

    /**
     * 批量删除结题报告
     * 
     * @param ids 需要删除的结题报告主键
     * @return 结果
     */
    @Override
    public int deleteProjectFinalReportByIds(Long[] ids)
    {
        return projectFinalReportMapper.deleteProjectFinalReportByIds(ids);
    }

    /**
     * 删除结题报告信息
     * 
     * @param id 结题报告主键
     * @return 结果
     */
    @Override
    public int deleteProjectFinalReportById(Long id)
    {
        return projectFinalReportMapper.deleteProjectFinalReportById(id);
    }
}
