package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.Project;
import com.ruoyi.startproject.domain.ProjectTree;
import com.ruoyi.startproject.domain.StartProject;
import com.ruoyi.system.domain.vo.Query;

import java.util.List;

public interface StartprojectService {
    /**
     * 查询项目列表
     * @param UserId
     * @return
     */
    public List<ProjectTree> selectProjectList(Long UserId);

    public List<StartProject> selectStartProjectList(Query query);
    public StartProject selectStartProjectById(Long id);

    public StartProject selectStartProjectByProjectId(Long projectId);
    public int insertStartProject(StartProject project);
    public int updateStartProject(StartProject project);
    public int deleteStartProjectById(Long id);

}
