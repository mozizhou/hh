package com.ruoyi.startproject.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.startproject.domain.EquipmentMain;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 设备主Service接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface IEquipmentMainService 
{

    public AjaxResult importEquipmentUsage(MultipartFile file, boolean updateSupport,String company) throws IOException;
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
     * 批量删除设备主
     * 
     * @param ids 需要删除的设备主主键集合
     * @return 结果
     */
    public int deleteEquipmentMainByIds(Long[] ids);

    /**
     * 删除设备主信息
     * 
     * @param id 设备主主键
     * @return 结果
     */
    public int deleteEquipmentMainById(Long id);
}
