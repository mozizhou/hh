package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.domain.ProjectTrialProductionFinalreport;
import com.ruoyi.startproject.domain.ProjectTrialProductionReport;
import com.ruoyi.startproject.service.IProjectTrialProductionReportService;
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
 * 试制终审报告Controller
 * 
 * @author ruoyi
 * @date 2025-06-20
 */
@RestController
@RequestMapping("/system/productreport")
public class ProjectTrialProductionReportController extends BaseController
{
    @Autowired
    private IProjectTrialProductionReportService projectTrialProductionReportService;

    /**
     * 查询试制终审报告列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProjectTrialProductionReport projectTrialProductionReport)
    {
        startPage();
        List<ProjectTrialProductionReport> list = projectTrialProductionReportService.selectProjectTrialProductionReportList(projectTrialProductionReport);
        return getDataTable(list);
    }

    @PostMapping("/listByid")
    public AjaxResult listByid(@RequestBody ProjectTrialProduction projectTrialProduction)
    {
        ProjectTrialProductionReport projectTrialProductionReport = projectTrialProductionReportService.selectProjectTrialProductionFinalreportByMainId(projectTrialProduction);
        return AjaxResult.success(projectTrialProductionReport);
    }

    /**
     * 导出试制终审报告列表
     */
    @PreAuthorize("@ss.hasPermi('system:report:export')")
    @Log(title = "试制终审报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTrialProductionReport projectTrialProductionReport)
    {
        List<ProjectTrialProductionReport> list = projectTrialProductionReportService.selectProjectTrialProductionReportList(projectTrialProductionReport);
        ExcelUtil<ProjectTrialProductionReport> util = new ExcelUtil<ProjectTrialProductionReport>(ProjectTrialProductionReport.class);
        util.exportExcel(response, list, "试制终审报告数据");
    }

    /**
     * 获取试制终审报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:report:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(projectTrialProductionReportService.selectProjectTrialProductionReportById(id));
    }

    /**
     * 新增试制终审报告
     */
    @PreAuthorize("@ss.hasPermi('system:report:add')")
    @Log(title = "试制终审报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTrialProductionReport projectTrialProductionReport)
    {
        return toAjax(projectTrialProductionReportService.insertProjectTrialProductionReport(projectTrialProductionReport));
    }

    /**
     * 修改试制终审报告
     */
    @Log(title = "试制终审报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTrialProductionReport projectTrialProductionReport)
    {
        return toAjax(projectTrialProductionReportService.updateProjectTrialProductionReport(projectTrialProductionReport));
    }

    /**
     * 删除试制终审报告
     */
    @PreAuthorize("@ss.hasPermi('system:report:remove')")
    @Log(title = "试制终审报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(projectTrialProductionReportService.deleteProjectTrialProductionReportByIds(ids));
    }
}
