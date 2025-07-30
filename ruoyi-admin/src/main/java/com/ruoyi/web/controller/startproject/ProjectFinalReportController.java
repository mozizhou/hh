package com.ruoyi.web.controller.startproject;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.startproject.domain.ProjectFinalReport;
import com.ruoyi.startproject.service.IProjectFinalReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 结题报告Controller
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@RestController
@RequestMapping("/system/report")
public class ProjectFinalReportController extends BaseController
{
    @Autowired
    private IProjectFinalReportService projectFinalReportService;

    /**
     * 查询结题报告列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectFinalReport projectFinalReport)
    {
        startPage();

        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectFinalReport.setCompany(user1.getCompany());
        }
        List<ProjectFinalReport> list = projectFinalReportService.selectProjectFinalReportList(projectFinalReport);
        return getDataTable(list);
    }

    /**
     * 导出结题报告列表
     */

    @Log(title = "结题报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectFinalReport projectFinalReport)
    {
        List<ProjectFinalReport> list = projectFinalReportService.selectProjectFinalReportList(projectFinalReport);
        ExcelUtil<ProjectFinalReport> util = new ExcelUtil<ProjectFinalReport>(ProjectFinalReport.class);
        util.exportExcel(response, list, "结题报告数据");
    }

    /**
     * 获取结题报告详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectFinalReportService.selectProjectFinalReportById(id));
    }

    /**
     * 新增结题报告
     */

    @Log(title = "结题报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectFinalReport projectFinalReport)
    {
        return toAjax(projectFinalReportService.insertProjectFinalReport(projectFinalReport));
    }

    /**
     * 修改结题报告
     */

    @Log(title = "结题报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectFinalReport projectFinalReport)
    {
        return toAjax(projectFinalReportService.updateProjectFinalReport(projectFinalReport));
    }

    /**
     * 删除结题报告
     */

    @Log(title = "结题报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectFinalReportService.deleteProjectFinalReportByIds(ids));
    }
}
