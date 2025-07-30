package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.ProjectTrialProduction;

import java.util.List;
/**
 * 试制与问题跟踪主Service接口
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public interface IProjectTrialProductionService 
{
    /**
     * 查询试制与问题跟踪主
     * 
     * @param id 试制与问题跟踪主主键
     * @return 试制与问题跟踪主
     */
    public ProjectTrialProduction selectProjectTrialProductionById(Long id);

    /**
     * 查询试制与问题跟踪主列表
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 试制与问题跟踪主集合
     */
    public List<ProjectTrialProduction> selectProjectTrialProductionList(ProjectTrialProduction projectTrialProduction);
    public List<ProjectTrialProduction> selectProjectTrialProductionListByMain(ProjectTrialProduction projectTrialProduction);

    /**
     * 新增试制与问题跟踪主
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 结果
     */
    public int insertProjectTrialProduction(ProjectTrialProduction projectTrialProduction);

    /**
     * 修改试制与问题跟踪主
     * 
     * @param projectTrialProduction 试制与问题跟踪主
     * @return 结果
     */
    public int updateProjectTrialProduction(ProjectTrialProduction projectTrialProduction);

    /**
     * 批量删除试制与问题跟踪主
     * 
     * @param ids 需要删除的试制与问题跟踪主主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionByIds(Long[] ids);

    /**
     * 删除试制与问题跟踪主信息
     * 
     * @param id 试制与问题跟踪主主键
     * @return 结果
     */
    public int deleteProjectTrialProductionById(Long id);
}
