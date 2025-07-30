package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.EquipmentMain;

import java.util.List;

/**
 * 设备主Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface EquipmentMainMapper 
{
    /**
     * 查询设备主
     * 
     * @param id 设备主主键
     * @return 设备主
     */
    public EquipmentMain selectEquipmentMainById(Long id);

    /**
     * 查询设备主列表
     * 
     * @param equipmentMain 设备主
     * @return 设备主集合
     */
    public List<EquipmentMain> selectEquipmentMainList(EquipmentMain equipmentMain);

    /**
     * 新增设备主
     * 
     * @param equipmentMain 设备主
     * @return 结果
     */
    public int insertEquipmentMain(EquipmentMain equipmentMain);

    /**
     * 修改设备主
     * 
     * @param equipmentMain 设备主
     * @return 结果
     */
    public int updateEquipmentMain(EquipmentMain equipmentMain);

    /**
     * 删除设备主
     * 
     * @param id 设备主主键
     * @return 结果
     */
    public int deleteEquipmentMainById(Long id);

    /**
     * 批量删除设备主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentMainByIds(Long[] ids);
}
