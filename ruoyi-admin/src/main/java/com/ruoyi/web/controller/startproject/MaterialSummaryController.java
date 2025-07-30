package com.ruoyi.web.controller.startproject;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.MaterialSummary;
import com.ruoyi.startproject.service.IMaterialSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 材料领用Controller
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@RestController
@RequestMapping("/system/summary")
public class MaterialSummaryController extends BaseController
{
    @Autowired
    private IMaterialSummaryService materialSummaryService;

    /**
     * 主页面查询(包含公司，项目，年，月查询和排序)
     */
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody MaterialSummary materialSummary)
    {
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().isEmpty()){
            materialSummary.setCompany(user1.getCompany());
        }
        List<MaterialSummary> list = materialSummaryService.selectMaterialSummaryListByorders(materialSummary);
        return getDataTable(list);
    }

    /**
     * 主页面查询(包含公司，项目，年，月查询和排序)
     */
    @PostMapping("/view")
    public AjaxResult view(@RequestBody MaterialSummary materialSummary)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().isEmpty()){
            materialSummary.setCompany(user1.getCompany());
        }
        Map<String, List<MaterialSummary>> view = materialSummaryService.view(materialSummary);
        return AjaxResult.success(view);
    }


    /**
     * 导出材料领用列表
     */
    @Log(title = "材料领用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialSummary materialSummary)
    {
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().isEmpty()){
            materialSummary.setCompany(user1.getCompany());
        }
        //导出模板里金额
        List<MaterialSummary> list = materialSummaryService.selectMaterialSummaryList(materialSummary);
        ExcelUtil<MaterialSummary> util = new ExcelUtil<>(MaterialSummary.class);
        util.exportExcel(response, list, "材料领用数据");
    }

    // MaterialSummaryController.java 新增以下方法


//    @Log(title = "材料领用", businessType = BusinessType.IMPORT)
//    @PostMapping("/importData")
//    public AjaxResult importData(@RequestParam("file") MultipartFile file, boolean updateSupport) throws Exception {
//        LoginUser loginUser = getLoginUser();
//
//        SysUser user1 = loginUser.getUser();
//
//        if (user1.getCompany()==null||user1.getCompany().equals("")){
//            return AjaxResult.error("请登录对应公司账号进行导入材料记录");
//        }
//        ExcelUtil<MaterialSummary> util = new ExcelUtil<>(MaterialSummary.class);
//        List<MaterialSummary> list = util.importExcel(file.getInputStream(), 0);
//        String message = materialSummaryService.importMaterialSummary(list, updateSupport, user1.getCompany());
//        return AjaxResult.success(message);
//    }

    /**
     * 导入设备使用记录
     */
    @Log(title = "材料领用", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file, boolean updateSupport) {
        try {
            LoginUser loginUser = getLoginUser();

            SysUser user1 = loginUser.getUser();
            if (user1.getCompany()==null||user1.getCompany().isEmpty()){
                return AjaxResult.error("请登录对应公司账号进行导入设备记录");
            }
            return materialSummaryService.importMaterialSummary(file, updateSupport, user1.getCompany());
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    /**
     * 导入设备使用记录按月份
     */
    @Log(title = "材料领用", businessType = BusinessType.IMPORT)
    @PostMapping("/importDataBymonth")
    public AjaxResult importDataBymonth(@RequestParam("file") MultipartFile file, boolean updateSupport) {
        try {
            LoginUser loginUser = getLoginUser();

            SysUser user1 = loginUser.getUser();
            if (user1.getCompany()==null||user1.getCompany().isEmpty()){
                return AjaxResult.error("请登录对应公司账号进行导入设备记录");
            }
            return materialSummaryService.importMaterialSummaryBYmonth(file, updateSupport, user1.getCompany());
        } catch (Exception e) {
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<MaterialSummary> util = new ExcelUtil<>(MaterialSummary.class);
        util.importTemplateExcel(response, "材料领用数据");
    }


    /**
     * 获取材料领用详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(materialSummaryService.selectMaterialSummaryById(id));
    }

    /**
     * 新增材料领用
     */
    @Log(title = "材料领用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialSummary materialSummary)
    {
        return toAjax(materialSummaryService.insertMaterialSummary(materialSummary));
    }

    /**
     * 修改材料领用
     */
    @Log(title = "材料领用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialSummary materialSummary)
    {
        return toAjax(materialSummaryService.updateMaterialSummary(materialSummary));
    }

    /**
     * 删除材料领用
     */
    @Log(title = "材料领用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(materialSummaryService.deleteMaterialSummaryByIds(ids));
    }
}
