package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionTableData;

import java.util.List;

/**
 * 试制与问题跟踪子(问题跟踪)Service接口
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public interface IProjectTrialProductionTableDataService 
{
    /**
     * 查询试制与问题跟踪子(问题跟踪)
     * 
     * @param id 试制与问题跟踪子(问题跟踪)主键
     * @return 试制与问题跟踪子(问题跟踪)
     */
    public ProjectTrialProductionTableData selectProjectTrialProductionTableDataById(Long id);

    /**
     * 查询试制与问题跟踪子(问题跟踪)列表
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 试制与问题跟踪子(问题跟踪)集合
     */
    public List<ProjectTrialProductionTableData> selectProjectTrialProductionTableDataList(ProjectTrialProductionTableData projectTrialProductionTableData);

    /**
     * 根据主表id查子表
     * @param projectTrialProduction
     * @return
     */
    public List<ProjectTrialProductionTableData> selectProductionTableDataById(ProjectTrialProduction projectTrialProduction);

    /**
     * 新增试制与问题跟踪子(问题跟踪)
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 结果
     */
    public int insertProjectTrialProductionTableData(ProjectTrialProductionTableData projectTrialProductionTableData);

    /**
     * 修改试制与问题跟踪子(问题跟踪)
     * 
     * @param projectTrialProductionTableData 试制与问题跟踪子(问题跟踪)
     * @return 结果
     */
    public int updateProjectTrialProductionTableData(ProjectTrialProductionTableData projectTrialProductionTableData);

    /**
     * 批量删除试制与问题跟踪子(问题跟踪)
     * 
     * @param ids 需要删除的试制与问题跟踪子(问题跟踪)主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionTableDataByIds(Long[] ids);

    /**
     * 删除试制与问题跟踪子(问题跟踪)信息
     * 
     * @param id 试制与问题跟踪子(问题跟踪)主键
     * @return 结果
     */
    public int deleteProjectTrialProductionTableDataById(Long id);
}
