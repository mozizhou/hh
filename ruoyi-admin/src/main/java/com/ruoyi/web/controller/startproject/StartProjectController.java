package com.ruoyi.web.controller.startproject;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.startproject.domain.ProjectTree;
import com.ruoyi.startproject.domain.StartProject;
import com.ruoyi.startproject.service.StartprojectService;
import com.ruoyi.system.domain.vo.Query;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.controller.util.WordExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 立项文件
 */
@RestController
@RequestMapping("/startproject/profile")
public class StartProjectController extends BaseController {
    @Autowired
    private StartprojectService  startprojectService;
    /**
     * 立项文件对应附件上传
     */
    @Log(title = "立项文件上传", businessType = BusinessType.INSERT)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception
    {
        if (!file.isEmpty())
        {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("Url", avatar);
            return ajax;
        }
        return error("上传文件异常，请联系管理员");
    }

    @Log(title = "立项文件上传", businessType = BusinessType.INSERT)
    @PostMapping("/avatarlist")
    public AjaxResult avatar(@RequestParam("files") MultipartFile[] files) throws Exception {
        if (files == null || files.length == 0) {
            return error("上传文件不能为空");
        }

        LoginUser loginUser = getLoginUser();
        List<String> urlList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 上传单个文件并获取URL
                String fileUrl = FileUploadUtils.upload(
                        RuoYiConfig.getAvatarPath(),
                        file,
                        MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION
                );
                urlList.add(fileUrl);
            }
        }

        if (urlList.isEmpty()) {
            return error("有效文件为空");
        }

        // 用逗号分隔URL
        String urls = String.join(",", urlList);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("urls", urls);
        return ajax;
    }
    /**
     * 下载文件
     */
//    @GetMapping("/download/{fileName:.+}")
//    public void fileDownload(@PathVariable("fileName") String fileName,
//                             HttpServletResponse response,
//                             HttpServletRequest request) throws Exception {
//        //todo 获取文件在服务器的真实路径(将fileName里的/profile替换为RuoYiConfig.getAvatarPath())
//        String filePath = null;
//        filePath.replaceAll("/profile", RuoYiConfig.getProfile());
////        String filePath = RuoYiConfig.getAvatarPath() + fileName;
//
//        // 检查文件是否存在
//        File file = new File(filePath);
//        if (!file.exists()) {
//            throw new Exception("文件不存在: " + fileName);
//        }
//
//        // 检查文件类型是否允许下载
//        if (!FileUtils.checkAllowDownload(fileName)) {
//            throw new Exception("不允许下载该类型文件: " + fileName);
//        }
//
//        // 设置响应头（处理文件名编码问题）
//        String realFileName = FileUtils.getName(fileName); // 获取不含路径的文件名
//        FileUtils.setAttachmentResponseHeader(response, realFileName);
//
//        // 输出文件流
//        try (OutputStream os = response.getOutputStream()) {
//            FileUtils.writeBytes(filePath, os);
//        } catch (IOException e) {
//            throw new Exception("文件下载失败: " + e.getMessage());
//        }
//    }
    /**
     * 查询公司和项目列表
     */
    @GetMapping("/list")
    public AjaxResult list()
    {
        LoginUser loginUser = getLoginUser();
        if (loginUser.getUserId()==null||loginUser.getUserId()==0){
            return AjaxResult.warn("请先登录");
        }

        List<ProjectTree> projectTrees = startprojectService.selectProjectList(loginUser.getUserId());
        return AjaxResult.success(projectTrees);

    }

    /**
     * 新增立项文件
     */
    @Log(title = "立项文件", businessType = BusinessType.INSERT)
    @PostMapping("persist")
    public AjaxResult persist(@RequestBody StartProject startProject)
    {
        try {
            int i = 0;
            if (startProject.getId() == null || startProject.getId().equals("")) {
                i = startprojectService.insertStartProject(startProject);
            } else {
                i = startprojectService.updateStartProject(startProject);
            }
            if (i==0){
                return AjaxResult.error("操作失败");
            }
            return AjaxResult.success();
        }catch (Exception e){
            return AjaxResult.error("操作失败");
        }
    }
    /**
     * 修改立项文件
     */
    @Log(title = "立项文件", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public AjaxResult update(@RequestBody StartProject startProject)
        {
            try {
                int i = startprojectService.updateStartProject(startProject);
                if (i!=0){
                    return AjaxResult.error("修改失败");
                }
                return AjaxResult.success();
            }catch (Exception e){
                return AjaxResult.error("修改失败");
            }
        }
    /**
     * 删除立项文件
      */
    @Log(title = "立项文件", businessType = BusinessType.DELETE)
    @PostMapping("delete")
    public AjaxResult delete(@RequestBody StartProject startProject)
    {
        try {
            int i = startprojectService.deleteStartProjectById(startProject.getId());
            if (i==0){
                return AjaxResult.error("删除失败");
            }
            return AjaxResult.success();
        }catch (Exception e){
            return AjaxResult.error("删除失败");
        }
    }
    /**
     * 查询立项文件
     */
    @GetMapping("/startList")
    public TableDataInfo startList(Query query)
    {
        startPage();

        LoginUser loginUser = getLoginUser();

        SysUser user1 = loginUser.getUser();

        if (user1.getCompany()!=null&&!user1.getCompany().equals("")){
            query.setCompany(user1.getCompany());
        }

        List<StartProject> startProjects = startprojectService.selectStartProjectList(query);

        return getDataTable(startProjects);
    }
    @GetMapping("/getStartProjectById")
    public AjaxResult getStartProjectById(Long id)
    {
        StartProject startProject = startprojectService.selectStartProjectById(id);
        return AjaxResult.success(startProject);
    }
    @GetMapping("/getStartProjectByProjectId")
    public AjaxResult getStartProjectByProjectId(Long projectId)
    {
        StartProject startProject = startprojectService.selectStartProjectByProjectId(projectId);
        return AjaxResult.success(startProject);
    }
}
