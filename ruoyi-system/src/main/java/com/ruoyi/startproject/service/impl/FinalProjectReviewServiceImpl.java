package com.ruoyi.startproject.service.impl;

import java.util.List;

import com.ruoyi.startproject.domain.FinalProjectReview;
import com.ruoyi.startproject.mapper.FinalProjectReviewMapper;
import com.ruoyi.startproject.service.IFinalProjectReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 项目终审Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-10
 */
@Service
public class FinalProjectReviewServiceImpl implements IFinalProjectReviewService
{
    @Autowired
    private FinalProjectReviewMapper finalProjectReviewMapper;

    /**
     * 查询项目终审
     * 
     * @param id 项目终审主键
     * @return 项目终审
     */
    @Override
    public FinalProjectReview selectFinalProjectReviewById(Long id)
    {
        return finalProjectReviewMapper.selectFinalProjectReviewById(id);
    }

    /**
     * 查询项目终审列表
     * 
     * @param finalProjectReview 项目终审
     * @return 项目终审
     */
    @Override
    public List<FinalProjectReview> selectFinalProjectReviewList(FinalProjectReview finalProjectReview)
    {
        return finalProjectReviewMapper.selectFinalProjectReviewList(finalProjectReview);
    }

    /**
     * 新增项目终审
     * 
     * @param finalProjectReview 项目终审
     * @return 结果
     */
    @Override
    public int insertFinalProjectReview(FinalProjectReview finalProjectReview)
    {
        return finalProjectReviewMapper.insertFinalProjectReview(finalProjectReview);
    }

    /**
     * 修改项目终审
     * 
     * @param finalProjectReview 项目终审
     * @return 结果
     */
    @Override
    public int updateFinalProjectReview(FinalProjectReview finalProjectReview)
    {
        return finalProjectReviewMapper.updateFinalProjectReview(finalProjectReview);
    }

    /**
     * 批量删除项目终审
     * 
     * @param ids 需要删除的项目终审主键
     * @return 结果
     */
    @Override
    public int deleteFinalProjectReviewByIds(Long[] ids)
    {
        return finalProjectReviewMapper.deleteFinalProjectReviewByIds(ids);
    }

    /**
     * 删除项目终审信息
     * 
     * @param id 项目终审主键
     * @return 结果
     */
    @Override
    public int deleteFinalProjectReviewById(Long id)
    {
        return finalProjectReviewMapper.deleteFinalProjectReviewById(id);
    }
}
