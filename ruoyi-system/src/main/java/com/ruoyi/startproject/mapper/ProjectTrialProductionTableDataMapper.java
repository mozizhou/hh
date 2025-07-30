package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.ProjectTrialProductionTableData;

import java.util.List;

/**
 * 试制与问题跟踪子(问题跟踪)Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
public interface ProjectTrialProductionTableDataMapper 
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

    public List<ProjectTrialProductionTableData>    selectProjectTrialProductionTableDataListByMainid (Long Id);


    /**
     * 查询试制与问题跟踪子(问题跟踪)列表根据id
     * @param id
     * @return
     */
//    public List<ProjectTrialProductionTableData> selectProjectTrialProductionTableDataList(Long id);

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
     * 删除试制与问题跟踪子(问题跟踪)
     * 
     * @param id 试制与问题跟踪子(问题跟踪)主键
     * @return 结果
     */
    public int deleteProjectTrialProductionTableDataById(Long id);

    /**
     * 批量删除试制与问题跟踪子(问题跟踪)
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectTrialProductionTableDataByIds(Long[] ids);


    void batchInsertProjectTrialProductionTableData(List<ProjectTrialProductionTableData> list);

    void batchUpdateProjectTrialProductionTableData(List<ProjectTrialProductionTableData> list);
}
