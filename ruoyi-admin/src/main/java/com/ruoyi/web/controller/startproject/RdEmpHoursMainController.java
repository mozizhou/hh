package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.RdEmpHoursMain;
import com.ruoyi.startproject.service.IRdEmpHoursMainService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 研发人员工时汇总主Controller
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/rdemp/main")
public class RdEmpHoursMainController extends BaseController
{
    @Autowired
    private IRdEmpHoursMainService rdEmpHoursMainService;

    /**
     * 查询研发人员工时汇总主列表（页面上所有查询都走这个接口）
     */
    @GetMapping("/list")
    public TableDataInfo list(RdEmpHoursMain rdEmpHoursMain)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().isEmpty()){
            rdEmpHoursMain.setCompany(user1.getCompany());
        }
        List<RdEmpHoursMain> list = rdEmpHoursMainService.selectRdEmpHoursMainList(rdEmpHoursMain);
        return getDataTable(list);
    }

    /**
     * 导出研发人员工时汇总主列表
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RdEmpHoursMain rdEmpHoursMain)
    {
        List<RdEmpHoursMain> list = rdEmpHoursMainService.selectRdEmpHoursMainList(rdEmpHoursMain);
        ExcelUtil<RdEmpHoursMain> util = new ExcelUtil<RdEmpHoursMain>(RdEmpHoursMain.class);
        util.exportExcel(response, list, "研发人员工时汇总主数据");
    }

    /**
     * 导入研发人员工时汇总主列表
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file, boolean updateSupport) {
        try {
            LoginUser loginUser = getLoginUser();

            SysUser user1 = loginUser.getUser();
            if (user1.getCompany()==null||user1.getCompany().isEmpty()){
                return AjaxResult.error("请登录对应公司账号进行导入设备记录");
            }
            return rdEmpHoursMainService.importRdEmpHoursMain(file, updateSupport, user1.getCompany(),user1.getUserName());
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 获取研发人员工时汇总主详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(rdEmpHoursMainService.selectRdEmpHoursMainById(id));
    }

    /**
     * 新增研发人员工时汇总主
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RdEmpHoursMain rdEmpHoursMain)
    {
        return toAjax(rdEmpHoursMainService.insertRdEmpHoursMain(rdEmpHoursMain));
    }

    /**
     * 修改研发人员工时汇总主
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RdEmpHoursMain rdEmpHoursMain)
    {
        return toAjax(rdEmpHoursMainService.updateRdEmpHoursMain(rdEmpHoursMain));
    }
    /**
     * 单个删除
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable String id)
        {
            return toAjax(rdEmpHoursMainService.deleteRdEmpHoursMainById(id));
        }

    /**
     * 删除研发人员工时汇总主
     */
    @Log(title = "研发人员工时汇总主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(rdEmpHoursMainService.deleteRdEmpHoursMainByIds(ids));
    }
}
