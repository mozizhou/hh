package com.ruoyi.startproject.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.startproject.domain.ProjectTrialFeedback;
import com.ruoyi.startproject.domain.ProjectTrialFeedbackTree;
import com.ruoyi.startproject.domain.ProjectTrialMain;
import com.ruoyi.startproject.mapper.ProjectTrialFeedbackMapper;
import com.ruoyi.startproject.mapper.ProjectTrialMainMapper;
import com.ruoyi.startproject.service.IProjectTrialMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@Service
public class ProjectTrialMainServiceImpl implements IProjectTrialMainService
{
    @Autowired
    private ProjectTrialMainMapper projectTrialMainMapper;
    @Autowired
    private ProjectTrialFeedbackMapper projectTrialFeedbackMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ProjectTrialMain selectProjectTrialMainById(Long id)
    {
        return projectTrialMainMapper.selectProjectTrialMainById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ProjectTrialMain> selectProjectTrialMainList(ProjectTrialMain projectTrialMain)
    {
        return projectTrialMainMapper.selectProjectTrialMainList(projectTrialMain);
    }
    @Override
    public ProjectTrialMain selectlistBymainid(ProjectTrialMain projectTrialMain) {
        List<ProjectTrialFeedback> projectTrialFeedbacks = projectTrialFeedbackMapper.selectProjectTrialFeedbackListByMainId(projectTrialMain.getId());
        //todo 将projectTrialFeedbacks根据index进行分组
        Map<Long, List<ProjectTrialFeedback>> groupedFeedbacks = projectTrialFeedbacks.stream()
                .collect(Collectors.groupingBy(ProjectTrialFeedback::getIndex));
        List<ProjectTrialFeedbackTree> projectTrialFeedbackTrees = new ArrayList<ProjectTrialFeedbackTree>();
        for (Map.Entry<Long, List<ProjectTrialFeedback>> entry : groupedFeedbacks.entrySet()) {
            ProjectTrialFeedbackTree projectTrialFeedbackTree = new ProjectTrialFeedbackTree();
            projectTrialFeedbackTree.setIndex(entry.getKey());
            projectTrialFeedbackTree.setTestList(entry.getValue());
            projectTrialFeedbackTrees.add(projectTrialFeedbackTree);
        }
        ProjectTrialMain projectTrialMain1 = projectTrialMainMapper.selectProjectTrialMainById(projectTrialMain.getId());
        projectTrialMain1.setTestFeedList(projectTrialFeedbackTrees);
//        projectTrialMain1.setTestFeedList(projectTrialFeedbacks);
        return projectTrialMain1;
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertProjectTrialMain(ProjectTrialMain projectTrialMain)
    {
        return projectTrialMainMapper.insertProjectTrialMain(projectTrialMain);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param projectTrialMain 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateProjectTrialMain(ProjectTrialMain projectTrialMain)
    {
        if (projectTrialMain.getTestFeedList() != null&&projectTrialMain.getTestFeedList().size()>0){
            for (ProjectTrialFeedbackTree projectTrialFeedbackTree : projectTrialMain.getTestFeedList()){
                projectTrialFeedbackTree.getTestList().forEach(projectTrialFeedback -> {
                    projectTrialFeedback.setMainid(projectTrialMain.getId());
                    if (projectTrialFeedback.getId() == null||projectTrialFeedback.getId() == 0){
                        projectTrialFeedbackMapper.insertProjectTrialFeedback(projectTrialFeedback);
                    }else{
                        projectTrialFeedbackMapper.updateProjectTrialFeedback(projectTrialFeedback);
                    }
                });
            }
            return projectTrialMainMapper.updateProjectTrialMain(projectTrialMain);
        }
        return projectTrialMainMapper.updateProjectTrialMain(projectTrialMain);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialMainByIds(Long[] ids)
    {
        return projectTrialMainMapper.deleteProjectTrialMainByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialMainById(Long id)
    {
        return projectTrialMainMapper.deleteProjectTrialMainById(id);
    }
}
