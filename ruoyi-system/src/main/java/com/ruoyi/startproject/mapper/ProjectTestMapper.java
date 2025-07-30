package com.ruoyi.startproject.mapper;

import java.util.List;
import com.ruoyi.startproject.domain.ProjectTest;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
public interface ProjectTestMapper 
{
    /**
     * 查询测试记录
     * 
     * @param id 测试记录主键
     * @return 测试记录
     */
    public ProjectTest selectProjectTestById(Long id);

    /**
     * 查询测试记录列表
     * 
     * @param projectTest 测试记录
     * @return 测试记录集合
     */
    public List<ProjectTest> selectProjectTestList(ProjectTest projectTest);
    public List<ProjectTest> selectProjectTestListByMain(ProjectTest projectTest);

    /**
     * 新增测试记录
     * 
     * @param projectTest 测试记录
     * @return 结果
     */
    public int insertProjectTest(ProjectTest projectTest);

    /**
     * 修改测试记录
     * 
     * @param projectTest 测试记录
     * @return 结果
     */
    public int updateProjectTest(ProjectTest projectTest);

    /**
     * 删除测试记录
     * 
     * @param id 测试记录主键
     * @return 结果
     */
    public int deleteProjectTestById(Long id);

    /**
     * 批量删除测试记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTestByIds(Long[] ids);

    List<ProjectTest> selectProjectTestListByProjectId(Long projectId);
}
