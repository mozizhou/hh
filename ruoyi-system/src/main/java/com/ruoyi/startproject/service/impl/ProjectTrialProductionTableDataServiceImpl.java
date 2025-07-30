package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionTableData;
import com.ruoyi.startproject.mapper.ProjectTrialProductionTableDataMapper;
import com.ruoyi.startproject.service.IProjectTrialProductionTableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 试制与问题跟踪子(问题跟踪)Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
@Service
public class ProjectTrialProductionTableDataServiceImpl implements IProjectTrialProductionTableDataService
{
    @Autowired
    private ProjectTrialProductionTableDataMapper projectTrialProductionTableDataMapper;

    /**
     * 查询试制与问题跟踪子(问题跟踪)
     * 
     * @param id 试制与问题跟踪子(问题跟踪)主键
     * @return 试制与问题跟踪子(问题跟踪)
     */
    @Override
    public ProjectTrialProductionTableData selectProjectTrialProductionTableDataById(Long id)
    {
        return projectTrialProductionTableDataMapper.selectProjectTrialProductionTableDataById(id);
    }

    /**
     * 查询试制与问题跟踪子(问题跟踪)列表
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 试制与问题跟踪子(问题跟踪)
     */
    @Override
    public List<ProjectTrialProductionTableData> selectProjectTrialProductionTableDataList(ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        return projectTrialProductionTableDataMapper.selectProjectTrialProductionTableDataList(projectTrialProductionTableData);
    }

    @Override
    public List<ProjectTrialProductionTableData> selectProductionTableDataById(ProjectTrialProduction projectTrialProduction) {
            return projectTrialProductionTableDataMapper.selectProjectTrialProductionTableDataListByMainid(projectTrialProduction.getId());
    }

    /**
     * 新增试制与问题跟踪子(问题跟踪)
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 结果
     */
    @Override
    public int insertProjectTrialProductionTableData(ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        return projectTrialProductionTableDataMapper.insertProjectTrialProductionTableData(projectTrialProductionTableData);
    }

    /**
     * 修改试制与问题跟踪子(问题跟踪)
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 结果
     */
    @Override
    public int updateProjectTrialProductionTableData(ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        return projectTrialProductionTableDataMapper.updateProjectTrialProductionTableData(projectTrialProductionTableData);
    }

    /**
     * 批量删除试制与问题跟踪子(问题跟踪)
     * 
     * @param ids 需要删除的试制与问题跟踪子(问题跟踪)主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionTableDataByIds(Long[] ids)
    {
        return projectTrialProductionTableDataMapper.deleteProjectTrialProductionTableDataByIds(ids);
    }

    /**
     * 删除试制与问题跟踪子(问题跟踪)信息
     * 
     * @param id 试制与问题跟踪子(问题跟踪)主键
     * @return 结果
     */
    @Override
    public int deleteProjectTrialProductionTableDataById(Long id)
    {
        return projectTrialProductionTableDataMapper.deleteProjectTrialProductionTableDataById(id);
    }
}
