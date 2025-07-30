package com.ruoyi.startproject.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionFinalreport;
import com.ruoyi.startproject.domain.ProjectTrialProductionTableData;
import com.ruoyi.startproject.mapper.ProjectTrialProductionFinalreportMapper;
import com.ruoyi.startproject.mapper.ProjectTrialProductionMapper;
import com.ruoyi.startproject.mapper.ProjectTrialProductionTableDataMapper;
import com.ruoyi.startproject.service.IProjectTrialProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 试制与问题跟踪主Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
@Service
public class ProjectTrialProductionServiceImpl implements IProjectTrialProductionService
{
    @Autowired
    private ProjectTrialProductionMapper projectTrialProductionMapper;
    @Autowired
    private ProjectTrialProductionTableDataMapper projectTrialProductionTableDataMapper;
    @Autowired
    private ProjectTrialProductionFinalreportMapper projectTrialProductionFinalreportMapper;

    /**
     * 查询试制与问题跟踪主
     * 
     * @param id 试制与问题跟踪主主键
     * @return 试制与问题跟踪主
     */
    @Override
    public ProjectTrialProduction selectProjectTrialProductionById(Long id)
    {
        return projectTrialProductionMapper.selectProjectTrialProductionById(id);
    }

    /**
     * 查询试制与问题跟踪主列表
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 试制与问题跟踪主
     */
    @Override
    public List<ProjectTrialProduction> selectProjectTrialProductionList(ProjectTrialProduction projectTrialProduction)
    {
        List<ProjectTrialProduction> projectTrialProductions = projectTrialProductionMapper.selectProjectTrialProductionList(projectTrialProduction);
        for (ProjectTrialProduction  pp : projectTrialProductions){
            List<ProjectTrialProductionTableData> projectTrialProductionTableData = projectTrialProductionTableDataMapper.selectProjectTrialProductionTableDataListByMainid(pp.getId());
            if (projectTrialProductionTableData != null && projectTrialProductionTableData.size() > 0){
                pp.setIstableData(true);
                pp.setTableData(projectTrialProductionTableData);
            }
        }
        return projectTrialProductions;
    }

    @Override
    public List<ProjectTrialProduction> selectProjectTrialProductionListByMain(ProjectTrialProduction projectTrialProduction) {
        return projectTrialProductionMapper.selectProjectTrialProductionListByMain(projectTrialProduction);
    }

    /**
     * 新增试制与问题跟踪主
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 结果
     */
    @Override
    public int insertProjectTrialProduction(ProjectTrialProduction projectTrialProduction)
    {
        return projectTrialProductionMapper.insertProjectTrialProduction(projectTrialProduction);
    }

    /**
     * 修改试制与问题跟踪主
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProjectTrialProduction(ProjectTrialProduction projectTrialProduction) {
        // 参数校验
        if (projectTrialProduction == null || projectTrialProduction.getId() == null || projectTrialProduction.getId() <= 0) {
            throw new IllegalArgumentException("项目ID不能为空且必须大于0");
        }

        List<ProjectTrialProductionTableData> tableDataList = projectTrialProduction.getTableData();
        if (tableDataList != null && !tableDataList.isEmpty()) {
            List<ProjectTrialProductionTableData> insertList = new ArrayList<>();
            List<ProjectTrialProductionTableData> updateList = new ArrayList<>();

            for (ProjectTrialProductionTableData data : tableDataList) {
                if (data == null) continue;

                if (data.getId() == null || data.getId() == 0) {
                    data.setTrialid(projectTrialProduction.getId());
                    insertList.add(data);
                } else {
                    updateList.add(data);
                }
            }

            // 批量插入
            if (!insertList.isEmpty()&&  insertList.size()>0) {
                for (ProjectTrialProductionTableData data : insertList){
                    projectTrialProductionTableDataMapper.insertProjectTrialProductionTableData(data);
                }
            }

            // 批量更新
            if (!updateList.isEmpty()&& updateList.size()>0) {
                for (ProjectTrialProductionTableData data : updateList){
                    projectTrialProductionTableDataMapper.updateProjectTrialProductionTableData(data);
                }
            }
        }

        return projectTrialProductionMapper.updateProjectTrialProduction(projectTrialProduction);
    }
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public int updateProjectTrialProduction(ProjectTrialProduction projectTrialProduction)
//    {
//
//        if (projectTrialProduction.getTableData() != null&&projectTrialProduction.getTableData().size()>0){
//            for (ProjectTrialProductionTableData projectTrialProductionTableData : projectTrialProduction.getTableData()){
//                if (projectTrialProductionTableData.getId() == null||  projectTrialProductionTableData.getId() == 0){
//                    projectTrialProductionTableData.setTrialid(projectTrialProduction.getId());
//                    projectTrialProductionTableDataMapper.insertProjectTrialProductionTableData(projectTrialProductionTableData);
//                }else{
//                    projectTrialProductionTableDataMapper.updateProjectTrialProductionTableData(projectTrialProductionTableData);
//                }
//            }
//        }
//        return projectTrialProductionMapper.updateProjectTrialProduction(projectTrialProduction);
//    }

    /**
     * 批量删除试制与问题跟踪主
     * 
     * @param ids 需要删除的试制与问题跟踪主主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionByIds(Long[] ids)
    {
        return projectTrialProductionMapper.deleteProjectTrialProductionByIds(ids);
    }

    /**
     * 删除试制与问题跟踪主信息
     * 
     * @param id 试制与问题跟踪主主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionById(Long id)
    {
        return projectTrialProductionMapper.deleteProjectTrialProductionById(id);
    }
}
