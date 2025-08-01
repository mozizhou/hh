package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.ProjectTest;
import com.ruoyi.startproject.domain.ProjectTrialProduction;
import com.ruoyi.startproject.service.IProjectTestService;
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
 * 【测试记录】Controller
 * 
 * @author ruoyi
 * @date 2025-06-17
 */
@RestController
@RequestMapping("/system/test")
public class ProjectTestController extends BaseController
{
    @Autowired
    private IProjectTestService projectTestService;

    /**
     * 查询【请填写功能名称】列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectTest projectTest)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectTest.setCompany(user1.getCompany());
        }
        List<ProjectTest> list = projectTestService.selectProjectTestList(projectTest);
        return getDataTable(list);
    }
    @PostMapping("/listByMain")
    public AjaxResult listByMain(@RequestBody ProjectTest projectTest)
    {
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectTest.setCompany(user1.getCompany());
        }
        List<ProjectTest> list = projectTestService.selectProjectTestListByMain(projectTest);
        return AjaxResult.success(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTest projectTest)
    {
        List<ProjectTest> list = projectTestService.selectProjectTestList(projectTest);
        ExcelUtil<ProjectTest> util = new ExcelUtil<ProjectTest>(ProjectTest.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTestService.selectProjectTestById(id));
    }

    /**
     * 新增【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTest projectTest)
    {
        return toAjax(projectTestService.insertProjectTest(projectTest));
    }

    /**
     * 修改【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTest projectTest)
    {
        return toAjax(projectTestService.updateProjectTest(projectTest));
    }

    /**
     * 删除【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTestService.deleteProjectTestByIds(ids));
    }
}
