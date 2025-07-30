package com.ruoyi.startproject.service;

import com.ruoyi.startproject.domain.HomePagePolicy;
import com.ruoyi.startproject.domain.ProjectTest;

import java.util.List;
import java.util.Map;

public interface IhomePagePolicyService {

    /**
     * 查询首页政策信息主键
     *
     * @param id 项目终审主键
     * @return 项目终审
     */


    public HomePagePolicy selectHomePagePolicyById(Long id);


    /**
     * 查询首页政策信息列表
     *
     * @param homePagePolicy 项目终审
     * @return 项目终审集合
     */
    public List<HomePagePolicy> selectHomePagePolicyList(HomePagePolicy homePagePolicy);

    /**
     * 新增首页政策信息
     *
     * @param homePagePolicy 项目终审
     * @return 结果
     */

    /**
     * 查询【请填写功能名称】列表
     *
     * @param homePagePolicy 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */






    public int insertHomePagePolicy(HomePagePolicy homePagePolicy);

    /**
     * 修改首页政策信息
     *
     * @param homePagePolicy 项目终审
     * @return 结果
     */
    public int updateHomePagePolicy(HomePagePolicy homePagePolicy);

    /**
     * 删除首页政策信息
     *
     * @param id 项目终审主键
     * @return 结果
     */
    public int deleteHomePagePolicyById(Long id);

    /**
     * 批量删除首页政策信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHomePagePolicyByIds(Long[] ids);









}
