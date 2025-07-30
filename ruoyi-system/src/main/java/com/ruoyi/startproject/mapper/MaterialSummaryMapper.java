package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.MaterialSummary;

import java.util.List;
/**
 * 材料领用Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
public interface MaterialSummaryMapper 
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
    public List<MaterialSummary> selectMaterialSummaryListAll(MaterialSummary materialSummary);

    public List<MaterialSummary> selectMaterialSummaryListByYear(MaterialSummary materialSummary);

    public List<MaterialSummary> selectMaterialSummaryListByMouth(MaterialSummary materialSummary);


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
     * 删除材料领用
     * 
     * @param id 材料领用主键
     * @return 结果
     */
    public int deleteMaterialSummaryById(Long id);

    /**
     * 批量删除材料领用
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialSummaryByIds(Long[] ids);
}
