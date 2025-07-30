package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectTrialFeedback;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface ProjectTrialFeedbackMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ProjectTrialFeedback selectProjectTrialFeedbackById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectTrialFeedback> selectProjectTrialFeedbackList(ProjectTrialFeedback projectTrialFeedback);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectTrialFeedback(ProjectTrialFeedback projectTrialFeedback);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectTrialFeedback 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectTrialFeedback(ProjectTrialFeedback projectTrialFeedback);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectTrialFeedbackById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTrialFeedbackByIds(Long[] ids);

    public List<ProjectTrialFeedback> selectProjectTrialFeedbackListByMainId(Long mainId);
}
