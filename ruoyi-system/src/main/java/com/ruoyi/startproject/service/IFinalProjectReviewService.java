package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.FinalProjectReview;

import java.util.List;

/**
 * 项目终审Service接口
 * 
 * @author ruoyi
 * @date 2025-06-10
 */
public interface IFinalProjectReviewService 
{
    /**
     * 查询项目终审
     * 
     * @param id 项目终审主键
     * @return 项目终审
     */
    public FinalProjectReview selectFinalProjectReviewById(Long id);

    /**
     * 查询项目终审列表
     * 
     * @param finalProjectReview 项目终审
     * @return 项目终审集合
     */
    public List<FinalProjectReview> selectFinalProjectReviewList(FinalProjectReview finalProjectReview);

    /**
     * 新增项目终审
     * 
     * @param finalProjectReview 项目终审
     * @return 结果
     */
    public int insertFinalProjectReview(FinalProjectReview finalProjectReview);

    /**
     * 修改项目终审
     * 
     * @param finalProjectReview 项目终审
     * @return 结果
     */
    public int updateFinalProjectReview(FinalProjectReview finalProjectReview);

    /**
     * 批量删除项目终审
     * 
     * @param ids 需要删除的项目终审主键集合
     * @return 结果
     */
    public int deleteFinalProjectReviewByIds(Long[] ids);

    /**
     * 删除项目终审信息
     * 
     * @param id 项目终审主键
     * @return 结果
     */
    public int deleteFinalProjectReviewById(Long id);
}
