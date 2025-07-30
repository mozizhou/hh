package com.ruoyi.web.controller.startproject;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.startproject.domain.FinalProjectReview;
import com.ruoyi.startproject.service.IFinalProjectReviewService;
import com.ruoyi.system.domain.vo.Query;
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
 * 项目终审Controller
 * 
 * @author ruoyi
 * @date 2025-06-10
 */
@RestController
@RequestMapping("/system/review")
public class FinalProjectReviewController extends BaseController
{
    @Autowired
    private IFinalProjectReviewService finalProjectReviewService;

    /**
     * 查询项目终审列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FinalProjectReview finalProjectReview)
    {
        startPage();
        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            finalProjectReview.setCompany(user1.getCompany());
        }
        List<FinalProjectReview> list = finalProjectReviewService.selectFinalProjectReviewList(finalProjectReview);
        return getDataTable(list);
    }

    /**
     * 导出项目终审列表
     */
    @Log(title = "项目终审", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinalProjectReview finalProjectReview)
    {
        List<FinalProjectReview> list = finalProjectReviewService.selectFinalProjectReviewList(finalProjectReview);
        ExcelUtil<FinalProjectReview> util = new ExcelUtil<FinalProjectReview>(FinalProjectReview.class);
        util.exportExcel(response, list, "项目终审数据");
    }

    /**
     * 获取项目终审详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(finalProjectReviewService.selectFinalProjectReviewById(id));
    }

    /**
     * 新增项目终审
     */
    @Log(title = "项目终审", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinalProjectReview finalProjectReview)
    {
        return toAjax(finalProjectReviewService.insertFinalProjectReview(finalProjectReview));
    }

    /**
     * 修改项目终审
     */
    @Log(title = "项目终审", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinalProjectReview finalProjectReview)
    {
        return toAjax(finalProjectReviewService.updateFinalProjectReview(finalProjectReview));
    }

    /**
     * 删除项目终审
     */
    @Log(title = "项目终审", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(finalProjectReviewService.deleteFinalProjectReviewByIds(ids));
    }
}
