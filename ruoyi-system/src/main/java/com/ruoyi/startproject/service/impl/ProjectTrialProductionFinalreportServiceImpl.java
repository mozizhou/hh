package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionFinalreport;
import com.ruoyi.startproject.mapper.ProjectTrialProductionFinalreportMapper;
import com.ruoyi.startproject.mapper.ProjectTrialProductionMapper;
import com.ruoyi.startproject.service.IProjectTrialProductionFinalreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 试制终审报告子Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
@Service
public class ProjectTrialProductionFinalreportServiceImpl implements IProjectTrialProductionFinalreportService
{
    @Autowired
    private ProjectTrialProductionFinalreportMapper projectTrialProductionFinalreportMapper;
    @Autowired
    private ProjectTrialProductionMapper projectTrialProductionMapper;

    /**
     * 查询试制终审报告子
     * 
     * @param id 试制终审报告子主键
     * @return 试制终审报告子
     */
    @Override
    public ProjectTrialProductionFinalreport selectProjectTrialProductionFinalreportById(Long id)
    {
        return projectTrialProductionFinalreportMapper.selectProjectTrialProductionFinalreportById(id);
    }

    @Override
    public ProjectTrialProductionFinalreport selectProjectTrialProductionFinalreportByMainId(ProjectTrialProduction projectTrialProduction) {
        if (projectTrialProduction.getId() == null|| projectTrialProduction.getId() == 0){
            throw new RuntimeException("主表id不能为空");
        }
        return  projectTrialProductionFinalreportMapper.selectProjectTrialProductionFinalreportByMainId(projectTrialProduction.getId());
    }

    /**
     * 查询试制终审报告子列表
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 试制终审报告子
     */
    @Override
    public List<ProjectTrialProductionFinalreport> selectProjectTrialProductionFinalreportList(ProjectTrialProductionFinalreport projectTrialProductionFinalreport)
    {
        return projectTrialProductionFinalreportMapper.selectProjectTrialProductionFinalreportList(projectTrialProductionFinalreport);
    }

    /**
     * 新增试制终审报告子
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 结果
     */
    @Override
    public int insertProjectTrialProductionFinalreport(ProjectTrialProductionFinalreport projectTrialProductionFinalreport)
    {
        return projectTrialProductionFinalreportMapper.insertProjectTrialProductionFinalreport(projectTrialProductionFinalreport);
    }

    /**
     * 修改试制终审报告子
     * 
     * @param projectTrialProductionFinalreport 试制终审报告子
     * @return 结果
     */
    @Override
    public int updateProjectTrialProductionFinalreport(ProjectTrialProductionFinalreport projectTrialProductionFinalreport)
    {
        if (projectTrialProductionFinalreport==null){
            throw new IllegalArgumentException("没有终审内容");
        }
        if (projectTrialProductionFinalreport == null || projectTrialProductionFinalreport.getTrialid() == null || projectTrialProductionFinalreport.getTrialid() <= 0) {
            throw new IllegalArgumentException("试制ID不能为空且必须大于0");
        }
        String status = projectTrialProductionFinalreport.getStatus();
        if (status == null || status.equals("")) {
            throw new IllegalArgumentException("请选择审核结果");
        }
        Long projectId = projectTrialProductionFinalreport.getProjectId();
        if (projectId == null || projectId <= 0) {
            throw new IllegalArgumentException("项目ID不能为空且必须大于0");
        }
        if (projectTrialProductionFinalreport.getId()!= null&& projectTrialProductionFinalreport.getId() > 0){
            ProjectTrialProduction projectTrialProduction = new ProjectTrialProduction();
            projectTrialProduction.setId(projectTrialProductionFinalreport.getTrialid());
            projectTrialProduction.setStatus(status);
            //修改主表状态
             projectTrialProductionMapper.updateStatusById(projectTrialProduction);
            return projectTrialProductionFinalreportMapper.updateProjectTrialProductionFinalreport(projectTrialProductionFinalreport);
        }else{
            ProjectTrialProduction projectTrialProduction = new ProjectTrialProduction();
            projectTrialProduction.setId(projectTrialProductionFinalreport.getTrialid());
            projectTrialProduction.setStatus(status);
            //修改主表状态
            projectTrialProductionMapper.updateStatusById(projectTrialProduction);
            return projectTrialProductionFinalreportMapper.insertProjectTrialProductionFinalreport(projectTrialProductionFinalreport);
        }
//
//        return projectTrialProductionFinalreportMapper.updateProjectTrialProductionFinalreport(projectTrialProductionFinalreport);
    }

//    @Override
//    public int updateProjectTrialProductionFinalreportByMain(ProjectTrialProduction projectTrialProduction) {
//        if (projectTrialProduction == null || projectTrialProduction.getId() == null || projectTrialProduction.getId() <= 0) {
//            throw new IllegalArgumentException("项目ID不能为空且必须大于0");
//        }
//        if (projectTrialProduction.getFinalReviewForm() == null){
//            throw new IllegalArgumentException("没有终审内容");
//        }
//        ProjectTrialProductionFinalreport finalReviewForm = projectTrialProduction.getFinalReviewForm();
//        if (finalReviewForm.getId() != null&& finalReviewForm.getId() > 0) {
//            projectTrialProductionFinalreportMapper.updateProjectTrialProductionFinalreport(finalReviewForm);
//        }else{
//            finalReviewForm.setTrialid(projectTrialProduction.getId());
//            projectTrialProductionFinalreportMapper.insertProjectTrialProductionFinalreport(finalReviewForm);
//        }
//        return projectTrialProductionMapper.updateProjectTrialProduction(finalReviewForm);
//    }

    /**
     * 批量删除试制终审报告子
     * 
     * @param ids 需要删除的试制终审报告子主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionFinalreportByIds(Long[] ids)
    {
        return projectTrialProductionFinalreportMapper.deleteProjectTrialProductionFinalreportByIds(ids);
    }

    /**
     * 删除试制终审报告子信息
     * 
     * @param id 试制终审报告子主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionFinalreportById(Long id)
    {
        return projectTrialProductionFinalreportMapper.deleteProjectTrialProductionFinalreportById(id);
    }
}
