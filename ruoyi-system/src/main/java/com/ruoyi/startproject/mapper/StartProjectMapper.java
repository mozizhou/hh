package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.StartProject;
import com.ruoyi.system.domain.vo.Query;

import java.util.List;

public interface StartProjectMapper {

    public List<StartProject> getStartProjectsFromDB(String company);
    public List<StartProject> selectStartProjectList(Query query);
    public StartProject selectStartProjectById(Long id);
    public int insertStartProject(StartProject project);
    public int updateStartProject(StartProject project);
    public int deleteStartProjectById(Long id);

    public StartProject selectStartProjectByProjectId(Long projectId);

}
