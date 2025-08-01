package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.startproject.domain.ProjectTrialFeedback;
import com.ruoyi.startproject.service.IProjectTrialFeedbackService;
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
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@RestController
@RequestMapping("/system/feedback")
public class ProjectTrialFeedbackController extends BaseController
{
    @Autowired
    private IProjectTrialFeedbackService projectTrialFeedbackService;

    /**
     * 查询【请填写功能名称】列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectTrialFeedback projectTrialFeedback)
    {
        startPage();
        List<ProjectTrialFeedback> list = projectTrialFeedbackService.selectProjectTrialFeedbackList(projectTrialFeedback);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTrialFeedback projectTrialFeedback)
    {
        List<ProjectTrialFeedback> list = projectTrialFeedbackService.selectProjectTrialFeedbackList(projectTrialFeedback);
        ExcelUtil<ProjectTrialFeedback> util = new ExcelUtil<ProjectTrialFeedback>(ProjectTrialFeedback.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTrialFeedbackService.selectProjectTrialFeedbackById(id));
    }

    /**
     * 新增【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTrialFeedback projectTrialFeedback)
    {
        return toAjax(projectTrialFeedbackService.insertProjectTrialFeedback(projectTrialFeedback));
    }

    /**
     * 修改【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTrialFeedback projectTrialFeedback)
    {
        return toAjax(projectTrialFeedbackService.updateProjectTrialFeedback(projectTrialFeedback));
    }

    /**
     * 删除【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTrialFeedbackService.deleteProjectTrialFeedbackByIds(ids));
    }
}
