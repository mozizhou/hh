package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.*;
import com.ruoyi.startproject.mapper.*;
import com.ruoyi.startproject.service.IProjectTrialProductionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 试制终审报告Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-20
 */
@Service
public class ProjectTrialProductionReportServiceImpl implements IProjectTrialProductionReportService
{
    @Autowired
    private ProjectTrialProductionReportMapper projectTrialProductionReportMapper;
    @Autowired
    private ProjectTrialProductionMapper projectTrialProductionMapper;
    @Autowired
    private ProjectTestMapper projectTestMapper;
    @Autowired
    private ProjectTrialMainMapper projectTrialMainMapper;
    @Autowired
    private ProjectFinalReportMapper projectFinalReportMapper;

    /**
     * 查询试制终审报告
     * 
     * @param id 试制终审报告主键
     * @return 试制终审报告
     */
    @Override
    public ProjectTrialProductionReport selectProjectTrialProductionReportById(String id)
    {
        return projectTrialProductionReportMapper.selectProjectTrialProductionReportById(id);
    }

    /**
     * 查询试制终审报告列表
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 试制终审报告
     */
    @Override
    public List<ProjectTrialProductionReport> selectProjectTrialProductionReportList(ProjectTrialProductionReport projectTrialProductionReport)
    {
        return projectTrialProductionReportMapper.selectProjectTrialProductionReportList(projectTrialProductionReport);
    }

    @Override
    public ProjectTrialProductionReport selectProjectTrialProductionFinalreportByMainId(ProjectTrialProduction projectTrialProduction) {
        if (projectTrialProduction.getId() == null|| projectTrialProduction.getId() == 0){
            throw new RuntimeException("主表id不能为空");
        }
        return  projectTrialProductionReportMapper.selectProjectTrialProductionFinalreportByMainId(projectTrialProduction.getId());
    }

    /**
     * 新增试制终审报告
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 结果
     */
    @Override
    public int insertProjectTrialProductionReport(ProjectTrialProductionReport projectTrialProductionReport)
    {
        return projectTrialProductionReportMapper.insertProjectTrialProductionReport(projectTrialProductionReport);
    }

    /**
     * 修改试制终审报告
     * 
     * @param projectTrialProductionReport 试制终审报告
     * @return 结果
     */
    @Override
    public int updateProjectTrialProductionReport(ProjectTrialProductionReport projectTrialProductionReport)
    {
        if (projectTrialProductionReport==null){
            throw new IllegalArgumentException("没有终审内容");
        }
        if (projectTrialProductionReport == null || projectTrialProductionReport.getTrialid() == null || projectTrialProductionReport.getTrialid() <= 0) {
            throw new IllegalArgumentException("试制ID不能为空且必须大于0");
        }
        String status = projectTrialProductionReport.getStatus();
        if (status == null || status.equals("")) {
            throw new IllegalArgumentException("请选择审核结果");
        }
        Long projectId = projectTrialProductionReport.getProjectId();
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("项目ID不能为空且必须大于0");
        }
        List<ProjectTest> projectTests = projectTestMapper.selectProjectTestListByProjectId(projectId);
        if (status.equals("20004")){
            if (projectTests==null||projectTests.size()==0){
                throw new IllegalArgumentException("请先完成测试等流程");
            }
            List<ProjectTrialMain> projectTrialMains = projectTrialMainMapper.selectProjectTrialMainListByProjectId(projectId);
            if (projectTrialMains==null||projectTrialMains.size()==0){
                throw new IllegalArgumentException("请先完成试用流程");
            }
//            List<ProjectFinalReport> projectFinalReports = projectFinalReportMapper.selectProjectFinalReportListByProjectId(projectId);
//            if (projectFinalReports==null||projectFinalReports.size()==0){
//                throw new IllegalArgumentException("请先完成结题流程");
//            }
        }

        if (projectTrialProductionReport.getId()!= null&& projectTrialProductionReport.getId() > 0){
            ProjectTrialProduction projectTrialProduction = new ProjectTrialProduction();
            projectTrialProduction.setId(projectTrialProductionReport.getTrialid());
            projectTrialProduction.setStatus(status);
            //修改主表状态
            return projectTrialProductionMapper.updateStatusById(projectTrialProduction);
        }else{
            ProjectTrialProduction projectTrialProduction = new ProjectTrialProduction();
            projectTrialProduction.setId(projectTrialProductionReport.getTrialid());
            projectTrialProduction.setStatus(status);
//            List<ProjectTrialProductionReport> projectTrialProductionReports = projectTrialProductionReportMapper.selectProjectTrialProductionFinalreportByTrialid(projectTrialProduction.getId());
//            if (projectTrialProductionReports!=null&&projectTrialProductionReports.size()>0){
//                throw new IllegalArgumentException("已存在对应终审内容，不可重复添加");
//            }
            return projectTrialProductionMapper.updateStatusById(projectTrialProduction);
        }
    }

    /**
     * 批量删除试制终审报告
     * 
     * @param ids 需要删除的试制终审报告主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionReportByIds(String[] ids)
    {
        return projectTrialProductionReportMapper.deleteProjectTrialProductionReportByIds(ids);
    }

    /**
     * 删除试制终审报告信息
     * 
     * @param id 试制终审报告主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionReportById(String id)
    {
        return projectTrialProductionReportMapper.deleteProjectTrialProductionReportById(id);
    }
}
