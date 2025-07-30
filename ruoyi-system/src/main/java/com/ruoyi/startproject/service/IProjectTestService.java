package com.ruoyi.startproject.service;

import java.util.List;
import com.ruoyi.startproject.domain.ProjectTest;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface IProjectTestService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ProjectTest selectProjectTestById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectTest 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ProjectTest> selectProjectTestList(ProjectTest projectTest);

    public List<ProjectTest> selectProjectTestListByMain(ProjectTest projectTest);

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectTest 【请填写功能名称】
     * @return 结果
     */
    public int insertProjectTest(ProjectTest projectTest);

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectTest 【请填写功能名称】
     * @return 结果
     */
    public int updateProjectTest(ProjectTest projectTest);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteProjectTestByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteProjectTestById(Long id);
}
