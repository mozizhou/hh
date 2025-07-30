package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionTableData;
import com.ruoyi.startproject.service.IProjectTrialProductionService;
import com.ruoyi.startproject.service.IProjectTrialProductionTableDataService;
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
 * 试制与问题跟踪子(问题跟踪)Controller
 * 
 * @author ruoyi
 * @date 2025-06-16
 */
@RestController
@RequestMapping("/system/data")
public class ProjectTrialProductionTableDataController extends BaseController
{
    @Autowired
    private IProjectTrialProductionTableDataService projectTrialProductionTableDataService;
    @Autowired
    private IProjectTrialProductionService projectTrialProductionService;

    /**
     * 查询试制与问题跟踪子(问题跟踪)列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        startPage();
        List<ProjectTrialProductionTableData> list = projectTrialProductionTableDataService.selectProjectTrialProductionTableDataList(projectTrialProductionTableData);
        return getDataTable(list);
    }

    /**
     * 查询试制与问题跟踪子(问题跟踪)列表
     */

    @PostMapping("/listByid")
    public AjaxResult listByid(@RequestBody ProjectTrialProduction projectTrialProduction)
    {
        if (projectTrialProduction.getId() == null|| projectTrialProduction.getId() == 0){
            throw new RuntimeException("主表id不能为空");
        }else{
            ProjectTrialProduction result =  projectTrialProductionService.selectProjectTrialProductionById(projectTrialProduction.getId());
            List<ProjectTrialProductionTableData> list = projectTrialProductionTableDataService.selectProductionTableDataById(projectTrialProduction);
            result.setTableData(list);
            return AjaxResult.success(result);
        }

    }

    /**
     * 导出试制与问题跟踪子(问题跟踪)列表
     */

    @Log(title = "试制与问题跟踪子(问题跟踪)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        List<ProjectTrialProductionTableData> list = projectTrialProductionTableDataService.selectProjectTrialProductionTableDataList(projectTrialProductionTableData);
        ExcelUtil<ProjectTrialProductionTableData> util = new ExcelUtil<ProjectTrialProductionTableData>(ProjectTrialProductionTableData.class);
        util.exportExcel(response, list, "试制与问题跟踪子(问题跟踪)数据");
    }

    /**
     * 获取试制与问题跟踪子(问题跟踪)详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTrialProductionTableDataService.selectProjectTrialProductionTableDataById(id));
    }

    /**
     * 新增试制与问题跟踪子(问题跟踪)
     */

    @Log(title = "试制与问题跟踪子(问题跟踪)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        return toAjax(projectTrialProductionTableDataService.insertProjectTrialProductionTableData(projectTrialProductionTableData));
    }

    /**
     * 修改试制与问题跟踪子(问题跟踪)
     */

    @Log(title = "试制与问题跟踪子(问题跟踪)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTrialProductionTableData projectTrialProductionTableData)
    {
        return toAjax(projectTrialProductionTableDataService.updateProjectTrialProductionTableData(projectTrialProductionTableData));
    }

    /**
     * 删除试制与问题跟踪子(问题跟踪)
     */

    @Log(title = "试制与问题跟踪子(问题跟踪)", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTrialProductionTableDataService.deleteProjectTrialProductionTableDataByIds(ids));
    }
}
