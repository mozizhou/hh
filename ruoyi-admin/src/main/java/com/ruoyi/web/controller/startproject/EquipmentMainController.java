package com.ruoyi.web.controller.startproject;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.EquipmentMain;
import com.ruoyi.startproject.domain.EquipmentUsage;
import com.ruoyi.startproject.service.IEquipmentMainService;
import com.ruoyi.startproject.service.IEquipmentUsageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备主Controller
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/system/eqemain")
public class EquipmentMainController extends BaseController
{
    @Autowired
    private IEquipmentMainService equipmentMainService;
    @Autowired
    private IEquipmentUsageService equipmentUsageService;

    /**
     * 查询设备主列表
     */
    @GetMapping("/list")
    public TableDataInfo list(EquipmentMain equipmentMain)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            equipmentMain.setCompany(user1.getCompany());
        }
        List<EquipmentMain> list = equipmentMainService.selectEquipmentMainList(equipmentMain);
        return getDataTable(list);
    }

    /**
     * 导入设备使用记录
     */
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file, boolean updateSupport) {
        try {
            LoginUser loginUser = getLoginUser();

            SysUser user1 = loginUser.getUser();
            if (user1.getCompany()==null||user1.getCompany().equals("")){
                return AjaxResult.error("请登录对应公司账号进行导入设备记录");
            }
            return equipmentMainService.importEquipmentUsage(file, updateSupport, user1.getCompany());
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 导出设备主列表
     */
    @Log(title = "设备主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentMain equipmentMain)
    {
        List<EquipmentMain> list = equipmentMainService.selectEquipmentMainList(equipmentMain);
        ExcelUtil<EquipmentMain> util = new ExcelUtil<EquipmentMain>(EquipmentMain.class);
        util.exportExcel(response, list, "设备主数据");
    }

    /**
     * 获取设备主详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipmentMainService.selectEquipmentMainById(id));
    }

    /**
     * 新增设备主
     */
    @Log(title = "设备主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentMain equipmentMain)
    {
        return toAjax(equipmentMainService.insertEquipmentMain(equipmentMain));
    }

    /**
     * 修改设备主
     */
    @Log(title = "设备主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentMain equipmentMain)
    {
        return toAjax(equipmentMainService.updateEquipmentMain(equipmentMain));
    }

    /**
     * 删除设备主
     */
    @Log(title = "设备主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipmentMainService.deleteEquipmentMainByIds(ids));
    }
}
