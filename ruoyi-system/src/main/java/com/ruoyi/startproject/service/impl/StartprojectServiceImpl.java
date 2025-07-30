package com.ruoyi.startproject.service.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.startproject.domain.Project;
import com.ruoyi.startproject.domain.ProjectTree;
import com.ruoyi.startproject.domain.StartProject;
import com.ruoyi.startproject.mapper.FinalProjectReviewMapper;
import com.ruoyi.startproject.mapper.StartProjectMapper;
import com.ruoyi.startproject.service.StartprojectService;
import com.ruoyi.system.domain.vo.Query;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StartprojectServiceImpl implements StartprojectService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private StartProjectMapper startProjectMapper;
    @Autowired
    private FinalProjectReviewMapper finalProjectReviewMapper;

    @Override
    public List<ProjectTree> selectProjectList(Long UserId) { 
        String companyName = userMapper.getCompanyName(UserId);

        // 假设这是从数据库查询出来的所有立项项目
        List<StartProject> startProjects = startProjectMapper.getStartProjectsFromDB(companyName);

        // 按 company 分组，并提取每个公司的 project 对象列表（包含 projectName 和 id）
        Map<String, List<Project>> projectMap = startProjects.stream()
                .collect(Collectors.groupingBy(
                        StartProject::getCompany,
                        Collectors.mapping(project -> new Project(project.getProjectName(),project.getProjectNo(), project.getId()), Collectors.toList())
                ));

        // 转换为 ProjectTree 列表
        List<ProjectTree> projectTrees = projectMap.entrySet().stream()
                .map(entry -> {
                    ProjectTree tree = new ProjectTree();
                    tree.setCompany(entry.getKey());
                    tree.setProjectNames(entry.getValue());
                    return tree;
                })
                .collect(Collectors.toList());

        return projectTrees;

    }

    @Override
    public List<StartProject> selectStartProjectList(Query query) {
        return startProjectMapper.selectStartProjectList(query);
    }

    @Override
    public StartProject selectStartProjectById(Long id) {
        return startProjectMapper.selectStartProjectById(id);
    }

    @Override
    public StartProject selectStartProjectByProjectId(Long projectId) {
        return null;
    }

    @Override
    public int insertStartProject(StartProject project) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(project);
            project.setJson(json); // 将对象序列化为 JSON 并设置到 json 字段
            return startProjectMapper.insertStartProject(project);
        } catch (JsonProcessingException e) {
            // 处理异常
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateStartProject(StartProject project) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            project.setJson(null);
            String json = objectMapper.writeValueAsString(project);
            project.setJson(json);
            return startProjectMapper.updateStartProject(project);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteStartProjectById(Long id) {
        if (startProjectMapper.deleteStartProjectById(id) > 0) {
            return 1;
        }
        return 0;
    }

}
