package com.ruoyi.startproject.mapper;

import com.ruoyi.startproject.domain.RdEmpHoursMain;

import java.util.List;
/**
 * 研发人员工时汇总主Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
public interface RdEmpHoursMainMapper 
{
    /**
     * 查询研发人员工时汇总主
     * 
     * @param id 研发人员工时汇总主主键
     * @return 研发人员工时汇总主
     */
    public RdEmpHoursMain selectRdEmpHoursMainById(String id);

    /**
     * 查询研发人员工时汇总主列表
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 研发人员工时汇总主集合
     */
    public List<RdEmpHoursMain> selectRdEmpHoursMainList(RdEmpHoursMain rdEmpHoursMain);

    public List<RdEmpHoursMain> getYears(RdEmpHoursMain rdEmpHoursMain);
    public List<RdEmpHoursMain> getMonths(RdEmpHoursMain rdEmpHoursMain);
    public List<RdEmpHoursMain> getprojectNames(RdEmpHoursMain rdEmpHoursMain);

    /**
     * 新增研发人员工时汇总主
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 结果
     */
    public int insertRdEmpHoursMain(RdEmpHoursMain rdEmpHoursMain);

    /**
     * 修改研发人员工时汇总主
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 结果
     */
    public int updateRdEmpHoursMain(RdEmpHoursMain rdEmpHoursMain);

    /**
     * 删除研发人员工时汇总主
     * 
     * @param id 研发人员工时汇总主主键
     * @return 结果
     */
    public int deleteRdEmpHoursMainById(String id);

    /**
     * 批量删除研发人员工时汇总主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRdEmpHoursMainByIds(String[] ids);
}
