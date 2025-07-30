package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectTrialMain;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface ProjectTrialMainMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ProjectTrialMain selectProjectTrialMainById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectTrialMain> selectProjectTrialMainList(ProjectTrialMain projectTrialMain);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectTrialMain(ProjectTrialMain projectTrialMain);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectTrialMain(ProjectTrialMain projectTrialMain);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectTrialMainById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTrialMainByIds(Long[] ids);

    List<ProjectTrialMain> selectProjectTrialMainListByProjectId(Long projectId);
}
