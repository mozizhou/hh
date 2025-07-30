package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.startproject.domain.EquipmentUsage;
import com.ruoyi.startproject.service.IEquipmentUsageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备使用记录Controller
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/system/usage")
public class EquipmentUsageController extends BaseController
{
    @Autowired
    private IEquipmentUsageService equipmentUsageService;

    /**
     * 查询设备使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:usage:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentUsage equipmentUsage)
    {
        startPage();
        List<EquipmentUsage> list = equipmentUsageService.selectEquipmentUsageList(equipmentUsage);
        return getDataTable(list);
    }

    /**
     * 导出设备使用记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:usage:export')")
    @Log(title = "设备使用记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentUsage equipmentUsage)
    {
        List<EquipmentUsage> list = equipmentUsageService.selectEquipmentUsageList(equipmentUsage);
        ExcelUtil<EquipmentUsage> util = new ExcelUtil<EquipmentUsage>(EquipmentUsage.class);
        util.exportExcel(response, list, "设备使用记录数据");
    }

    /**
     * 获取设备使用记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:usage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipmentUsageService.selectEquipmentUsageById(id));
    }

    /**
     * 新增设备使用记录
     */
    @PreAuthorize("@ss.hasPermi('system:usage:add')")
    @Log(title = "设备使用记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentUsage equipmentUsage)
    {
        return toAjax(equipmentUsageService.insertEquipmentUsage(equipmentUsage));
    }

    /**
     * 修改设备使用记录
     */
    @PreAuthorize("@ss.hasPermi('system:usage:edit')")
    @Log(title = "设备使用记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentUsage equipmentUsage)
    {
        return toAjax(equipmentUsageService.updateEquipmentUsage(equipmentUsage));
    }

    /**
     * 删除设备使用记录
     */
    @Log(title = "设备使用记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipmentUsageService.deleteEquipmentUsageByIds(ids));
    }
}
