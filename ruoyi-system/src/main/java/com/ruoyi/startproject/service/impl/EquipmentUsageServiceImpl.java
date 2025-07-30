package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.EquipmentUsage;
import com.ruoyi.startproject.mapper.EquipmentUsageMapper;
import com.ruoyi.startproject.service.IEquipmentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备使用记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class EquipmentUsageServiceImpl implements IEquipmentUsageService
{
    @Autowired
    private EquipmentUsageMapper equipmentUsageMapper;

    /**
     * 查询设备使用记录
     * 
     * @param id 设备使用记录主键
     * @return 设备使用记录
     */
    @Override
    public EquipmentUsage selectEquipmentUsageById(Long id)
    {
        return equipmentUsageMapper.selectEquipmentUsageById(id);
    }

    /**
     * 查询设备使用记录列表
     * 
     * @param equipmentUsage 设备使用记录
     * @return 设备使用记录
     */
    @Override
    public List<EquipmentUsage> selectEquipmentUsageList(EquipmentUsage equipmentUsage)
    {
        return equipmentUsageMapper.selectEquipmentUsageList(equipmentUsage);
    }

    /**
     * 新增设备使用记录
     * 
     * @param equipmentUsage 设备使用记录
     * @return 结果
     */
    @Override
    public int insertEquipmentUsage(EquipmentUsage equipmentUsage)
    {
        return equipmentUsageMapper.insertEquipmentUsage(equipmentUsage);
    }

    /**
     * 修改设备使用记录
     * 
     * @param equipmentUsage 设备使用记录
     * @return 结果
     */
    @Override
    public int updateEquipmentUsage(EquipmentUsage equipmentUsage)
    {
        return equipmentUsageMapper.updateEquipmentUsage(equipmentUsage);
    }

    /**
     * 批量删除设备使用记录
     * 
     * @param ids 需要删除的设备使用记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentUsageByIds(Long[] ids)
    {
        return equipmentUsageMapper.deleteEquipmentUsageByIds(ids);
    }

    /**
     * 删除设备使用记录信息
     * 
     * @param id 设备使用记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentUsageById(Long id)
    {
        return equipmentUsageMapper.deleteEquipmentUsageById(id);
    }
}
