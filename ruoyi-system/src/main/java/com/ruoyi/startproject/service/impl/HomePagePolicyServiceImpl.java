package com.ruoyi.startproject.service.impl;

import com.ruoyi.startproject.domain.HomePagePolicy;
import com.ruoyi.startproject.mapper.HomePagePolicyMapper;
import com.ruoyi.startproject.service.IhomePagePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class HomePagePolicyServiceImpl implements IhomePagePolicyService {






   @Autowired
   private  HomePagePolicyMapper homePagePolicyMapper;


   /**查询首页政策记录*/
   @Override
   public HomePagePolicy selectHomePagePolicyById(Long id)
   {
       return homePagePolicyMapper.selectHomePagePolicyById(id);
   }






   /**查询首页政策列表*/
   @Override
   public List<HomePagePolicy> selectHomePagePolicyList(HomePagePolicy homePagePolicy){
       return homePagePolicyMapper.selectHomePagePolicyList(homePagePolicy);

   }



   /**新增首页政策*/
   @Override
   public int insertHomePagePolicy(HomePagePolicy homePagePolicy){
       return homePagePolicyMapper.insertHomePagePolicy(homePagePolicy);
   }


   /**修改首页政策*/
   @Override
   public int updateHomePagePolicy(HomePagePolicy homePagePolicy){
       return homePagePolicyMapper.updateHomePagePolicy(homePagePolicy);
   }

   /**删除首页政策对象*/
   @Override
   public int deleteHomePagePolicyById(Long id){
       return homePagePolicyMapper.deleteHomePagePolicyById(id);
   }

   /**批量删除首页政策对象*/
    @Override
    public int deleteHomePagePolicyByIds(Long[] ids){
        return homePagePolicyMapper.deleteHomePagePolicyByIds(ids);
    }











}
