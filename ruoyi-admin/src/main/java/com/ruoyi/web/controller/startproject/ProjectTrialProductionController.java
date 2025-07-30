package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.service.IProjectTrialProductionService;
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
 * 试制与问题跟踪主Controller
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
@RestController
@RequestMapping("/system/production")
public class ProjectTrialProductionController extends BaseController
{
    @Autowired
    private IProjectTrialProductionService projectTrialProductionService;

    /**
     * 查询试制与问题跟踪主列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectTrialProduction projectTrialProduction)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectTrialProduction.setCompany(user1.getCompany());
        }
        List<ProjectTrialProduction> list = projectTrialProductionService.selectProjectTrialProductionList(projectTrialProduction);
        return getDataTable(list);
    }
    @PostMapping("/listByMain")
    public AjaxResult listByMain(@RequestBody ProjectTrialProduction projectTrialProduction)
    {
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectTrialProduction.setCompany(user1.getCompany());
        }
        List<ProjectTrialProduction> list = projectTrialProductionService.selectProjectTrialProductionListByMain(projectTrialProduction);
        return AjaxResult.success(list);
    }

    /**
     * 导出试制与问题跟踪主列表
     */

    @Log(title = "试制与问题跟踪主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTrialProduction projectTrialProduction)
    {
        List<ProjectTrialProduction> list = projectTrialProductionService.selectProjectTrialProductionList(projectTrialProduction);
        ExcelUtil<ProjectTrialProduction> util = new ExcelUtil<ProjectTrialProduction>(ProjectTrialProduction.class);
        util.exportExcel(response, list, "试制与问题跟踪主数据");
    }

    /**
     * 获取试制与问题跟踪主详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTrialProductionService.selectProjectTrialProductionById(id));
    }

    /**
     * 新增试制与问题跟踪主
     */

    @Log(title = "试制与问题跟踪主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTrialProduction projectTrialProduction)
    {
        return toAjax(projectTrialProductionService.insertProjectTrialProduction(projectTrialProduction));
    }

    /**
     * 修改试制与问题跟踪主
     */

    @Log(title = "试制与问题跟踪主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTrialProduction projectTrialProduction)
    {
        return toAjax(projectTrialProductionService.updateProjectTrialProduction(projectTrialProduction));
    }

    /**
     * 删除试制与问题跟踪主
     */

    @Log(title = "试制与问题跟踪主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTrialProductionService.deleteProjectTrialProductionByIds(ids));
    }
}
