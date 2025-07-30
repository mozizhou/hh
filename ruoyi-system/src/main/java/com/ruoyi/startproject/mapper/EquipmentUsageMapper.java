package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.EquipmentUsage;

import java.util.List;

/**
 * 设备使用记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface EquipmentUsageMapper 
{
    /**
     * 查询设备使用记录
     * 
     * @param id 设备使用记录主键
     * @return 设备使用记录
     */
    public EquipmentUsage selectEquipmentUsageById(Long id);

    /**
     * 查询设备使用记录列表
     * 
     * @param equipmentUsage 设备使用记录
     * @return 设备使用记录集合
     */
    public List<EquipmentUsage> selectEquipmentUsageList(EquipmentUsage equipmentUsage);

    /**
     * 查询设备使用记录列表ByID
     *
     * @param mainId 设备使用记录
     * @return 设备使用记录集合
     */
    public List<EquipmentUsage> selectEquipmentUsageListByID(Long mainId);

    /**
     * 新增设备使用记录
     * 
     * @param equipmentUsage 设备使用记录
     * @return 结果
     */
    public int insertEquipmentUsage(EquipmentUsage equipmentUsage);

    /**
     * 修改设备使用记录
     * 
     * @param equipmentUsage 设备使用记录
     * @return 结果
     */
    public int updateEquipmentUsage(EquipmentUsage equipmentUsage);

    /**
     * 删除设备使用记录
     * 
     * @param id 设备使用记录主键
     * @return 结果
     */
    public int deleteEquipmentUsageById(Long id);

    /**
     * 批量删除设备使用记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentUsageByIds(Long[] ids);

    public int deleteEquipmentUsageByMainId(Long mainId);
}
