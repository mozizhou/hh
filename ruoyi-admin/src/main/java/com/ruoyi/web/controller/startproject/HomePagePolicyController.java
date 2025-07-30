package com.ruoyi.web.controller.startproject;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.startproject.domain.HomePagePolicy;
import com.ruoyi.startproject.service.IhomePagePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 首页政策管理Controller
 */
@RestController
@RequestMapping("/system/homepagepolicy")
public class HomePagePolicyController extends BaseController{
   @Autowired
   private IhomePagePolicyService homePagePolicyService;


   @PostMapping("/list")
    public TableDataInfo list(@RequestBody HomePagePolicy homePagePolicy){
       List<HomePagePolicy> list = homePagePolicyService.selectHomePagePolicyList(homePagePolicy);

       return getDataTable(list);

   }

   @Log(title = "导出政策列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomePagePolicy homePagePolicy)
        {
        List<HomePagePolicy> list = homePagePolicyService.selectHomePagePolicyList(homePagePolicy);
        ExcelUtil<HomePagePolicy> util = new ExcelUtil<HomePagePolicy>(HomePagePolicy.class);
        util.exportExcel(response, list, "政策列表数据");
    }


    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(homePagePolicyService.selectHomePagePolicyById(id));
    }


    @Log(title = "新增政策", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomePagePolicy homePagePolicy)
    {
        return toAjax(homePagePolicyService.insertHomePagePolicy(homePagePolicy));
    }

    @Log(title = "修改政策", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomePagePolicy homePagePolicy)
    {
        return toAjax(homePagePolicyService.updateHomePagePolicy(homePagePolicy));
    }

    @Log(title = "删除政策", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homePagePolicyService.deleteHomePagePolicyByIds(ids));
    }




}
