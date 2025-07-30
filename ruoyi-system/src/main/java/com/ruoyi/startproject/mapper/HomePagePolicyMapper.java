package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.HomePagePolicy;

import java.util.List;

public interface HomePagePolicyMapper {



    public HomePagePolicy selectHomePagePolicyById(Long id);





    public List<HomePagePolicy> selectHomePagePolicyList(HomePagePolicy homePagePolicy);



    public int insertHomePagePolicy(HomePagePolicy finalProjectReview);



    public int updateHomePagePolicy(HomePagePolicy finalProjectReview);


    public int deleteHomePagePolicyById(Long id);

    public int deleteHomePagePolicyByIds(Long[] ids);












}
