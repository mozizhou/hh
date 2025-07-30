package com.ruoyi.startproject.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.startproject.domain.MaterialSummary;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 材料领用Service接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface IMaterialSummaryService 
{
    /**
     * 查询材料领用
     * 
     * @param id 材料领用主键
     * @return 材料领用
     */
    public MaterialSummary selectMaterialSummaryById(Long id);

    /**
     * 查询材料领用列表
     * 
     * @param materialSummary 材料领用
     * @return 材料领用集合
     */
    public List<MaterialSummary> selectMaterialSummaryList(MaterialSummary materialSummary);

    public Map<String, List<MaterialSummary>> view(MaterialSummary materialSummary);

    public List<MaterialSummary> selectMaterialSummaryListByorders(MaterialSummary materialSummary);

    // IMaterialSummaryService.java 新增方法

//    String importMaterialSummary(List<MaterialSummary> materialSummaryList, boolean updateSupport,String company);
    public AjaxResult importMaterialSummary(MultipartFile file, boolean updateSupport, String company) throws IOException;

    public AjaxResult importMaterialSummaryBYmonth(MultipartFile file, boolean updateSupport, String company) throws IOException;


    /**
     * 新增材料领用
     * 
     * @param materialSummary 材料领用
     * @return 结果
     */
    public int insertMaterialSummary(MaterialSummary materialSummary);

    /**
     * 修改材料领用
     * 
     * @param materialSummary 材料领用
     * @return 结果
     */
    public int updateMaterialSummary(MaterialSummary materialSummary);

    /**
     * 批量删除材料领用
     * 
     * @param ids 需要删除的材料领用主键集合
     * @return 结果
     */
    public int deleteMaterialSummaryByIds(Long[] ids);

    /**
     * 删除材料领用信息
     * 
     * @param id 材料领用主键
     * @return 结果
     */
    public int deleteMaterialSummaryById(Long id);
}
