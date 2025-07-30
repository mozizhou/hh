package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.ProjectTrialFeedback;
import com.ruoyi.startproject.mapper.ProjectTrialFeedbackMapper;
import com.ruoyi.startproject.service.IProjectTrialFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class ProjectTrialFeedbackServiceImpl implements IProjectTrialFeedbackService
{
    @Autowired
    private ProjectTrialFeedbackMapper projectTrialFeedbackMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ProjectTrialFeedback selectProjectTrialFeedbackById(Long id)
    {
        return projectTrialFeedbackMapper.selectProjectTrialFeedbackById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ProjectTrialFeedback> selectProjectTrialFeedbackList(ProjectTrialFeedback projectTrialFeedback)
    {
        return projectTrialFeedbackMapper.selectProjectTrialFeedbackList(projectTrialFeedback);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProjectTrialFeedback(ProjectTrialFeedback projectTrialFeedback)
    {
        return projectTrialFeedbackMapper.insertProjectTrialFeedback(projectTrialFeedback);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProjectTrialFeedback(ProjectTrialFeedback projectTrialFeedback)
    {
        return projectTrialFeedbackMapper.updateProjectTrialFeedback(projectTrialFeedback);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialFeedbackByIds(Long[] ids)
    {
        return projectTrialFeedbackMapper.deleteProjectTrialFeedbackByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialFeedbackById(Long id)
    {
        return projectTrialFeedbackMapper.deleteProjectTrialFeedbackById(id);
    }
}
