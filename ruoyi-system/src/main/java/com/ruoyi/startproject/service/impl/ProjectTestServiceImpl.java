package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.mapper.ProjectTestMapper;
import com.ruoyi.startproject.domain.ProjectTest;
import com.ruoyi.startproject.service.IProjectTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class ProjectTestServiceImpl implements IProjectTestService 
{
    @Autowired
    private ProjectTestMapper projectTestMapper;

    /**
     * 查询测试记录
     * 
     * @param id 测试记录主键
     * @return 测试记录
     */
    @Override
    public ProjectTest selectProjectTestById(Long id)
    {
        return projectTestMapper.selectProjectTestById(id);
    }

    /**
     * 查询测试记录列表
     * 
     * @param projectTest 测试记录
     * @return 测试记录
     */
    @Override
    public List<ProjectTest> selectProjectTestList(ProjectTest projectTest)
    {
        return projectTestMapper.selectProjectTestList(projectTest);
    }

    @Override
    public List<ProjectTest> selectProjectTestListByMain(ProjectTest projectTest) {
        return projectTestMapper.selectProjectTestListByMain(projectTest);
    }

    /**
     * 新增测试记录
     *
     * @param projectTest 测试记录
     * @return 结果
     */
    @Override
    public int insertProjectTest(ProjectTest projectTest)
    {
        return projectTestMapper.insertProjectTest(projectTest);
    }

    /**
     * 修改测试记录
     * 
     * @param projectTest 测试记录
     * @return 结果
     */
    @Override
    public int updateProjectTest(ProjectTest projectTest)
    {
        return projectTestMapper.updateProjectTest(projectTest);
    }

    /**
     * 批量删除测试记录
     * 
     * @param ids 需要删除的测试记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectTestByIds(Long[] ids)
    {
        return projectTestMapper.deleteProjectTestByIds(ids);
    }

    /**
     * 删除测试记录信息
     * 
     * @param id 测试记录主键
     * @return 结果
     */
    @Override
    public int deleteProjectTestById(Long id)
    {
        return projectTestMapper.deleteProjectTestById(id);
    }
}
