package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.ProjectTrialMain;
import com.ruoyi.startproject.service.IProjectTrialMainService;
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
@RequestMapping("/system/main")
public class ProjectTrialMainController extends BaseController
{
    @Autowired
    private IProjectTrialMainService projectTrialMainService;

    /**
     * 查询【主表查询】列表
     */

    @GetMapping("/list")
    public TableDataInfo list(ProjectTrialMain projectTrialMain)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            projectTrialMain.setCompany(user1.getCompany());
        }
        List<ProjectTrialMain> list = projectTrialMainService.selectProjectTrialMainList(projectTrialMain);
        return getDataTable(list);
    }
    /**
     * 根据主表查子表内容
     */
    @GetMapping("/listBymainid")
    public AjaxResult listBymainid(ProjectTrialMain projectTrialMain)
    {
        ProjectTrialMain result =  projectTrialMainService.selectlistBymainid(projectTrialMain);
        return AjaxResult.success(result);
    }

    /**
     * 导出【请填写功能名称】列表
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectTrialMain projectTrialMain)
    {
        List<ProjectTrialMain> list = projectTrialMainService.selectProjectTrialMainList(projectTrialMain);
        ExcelUtil<ProjectTrialMain> util = new ExcelUtil<ProjectTrialMain>(ProjectTrialMain.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */

    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectTrialMainService.selectProjectTrialMainById(id));
    }

    /**
     * 新增【请填写功能名称】
     */

    @Log(title = "【产品试用主表新增】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectTrialMain projectTrialMain)
    {
        return toAjax(projectTrialMainService.insertProjectTrialMain(projectTrialMain));
    }

    /**
     * 修改【请填写功能名称】
     */

    @Log(title = "【产品试用主表修改以及子表新增和修改】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectTrialMain projectTrialMain)
    {
        return toAjax(projectTrialMainService.updateProjectTrialMain(projectTrialMain));
    }

    /**
     * 删除【请填写功能名称】
     */

    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectTrialMainService.deleteProjectTrialMainByIds(ids));
    }
}
