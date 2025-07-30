package com.ruoyi.startproject.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.startproject.domain.MaterialSummary;
import com.ruoyi.startproject.domain.RdEmpHoursMain;
import com.ruoyi.startproject.mapper.RdEmpHoursMainMapper;
import com.ruoyi.startproject.service.IRdEmpHoursMainService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 研发人员工时汇总主Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-29
 */
@Service
public class RdEmpHoursMainServiceImpl implements IRdEmpHoursMainService
{
    @Autowired
    private RdEmpHoursMainMapper rdEmpHoursMainMapper;

    /**
     * 查询研发人员工时汇总主
     * 
     * @param id 研发人员工时汇总主主键
     * @return 研发人员工时汇总主
     */
    @Override
    public RdEmpHoursMain selectRdEmpHoursMainById(String id)
    {
        return rdEmpHoursMainMapper.selectRdEmpHoursMainById(id);
    }

    /**
     * 查询研发人员工时汇总主列表
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 研发人员工时汇总主
     */
    @Override
    public List<RdEmpHoursMain> selectRdEmpHoursMainList(RdEmpHoursMain rdEmpHoursMain)
    {
        //什么查询条件都没有的则查询年份和工时合计
        if (rdEmpHoursMain.getWorkYear()==null||rdEmpHoursMain.getWorkYear().equals("")){
            return rdEmpHoursMainMapper.getYears(rdEmpHoursMain);
        }
        //如果有公司和年份条件查询且没有月份查询的 查询月份和工时合计
        if ((rdEmpHoursMain.getWorkYear()!=null&&!rdEmpHoursMain.getWorkYear().equals(""))&&(rdEmpHoursMain.getWorkMonth()==null||rdEmpHoursMain.getWorkMonth().equals(""))){
            return rdEmpHoursMainMapper.getMonths(rdEmpHoursMain);

        }
        //有公司和年份条件查询且有月份查询的,查询对应项目
        if ((rdEmpHoursMain.getWorkYear()!=null&&!rdEmpHoursMain.getWorkYear().equals(""))&&(rdEmpHoursMain.getWorkMonth()!=null&&!rdEmpHoursMain.getWorkMonth().equals(""))&&(rdEmpHoursMain.getProjectName()==null||rdEmpHoursMain.getProjectName().equals(""))){
            return  rdEmpHoursMainMapper.getprojectNames(rdEmpHoursMain);
        }
        return rdEmpHoursMainMapper.selectRdEmpHoursMainList(rdEmpHoursMain);
    }

    @Override
    public AjaxResult importRdEmpHoursMain(MultipartFile file, boolean updateSupport, String company,String  username) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) { // 处理xlsx格式，如需处理xls请改用HSSFWorkbook

            List<RdEmpHoursMain> importDataList = new ArrayList<>();
            int sheetCount = workbook.getNumberOfSheets();

            // 遍历所有工作表
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet == null || sheet.getLastRowNum() < 2) { // 至少需要表头行+数据行
                    continue;
                }
                // 解析当前工作表数据
                List<RdEmpHoursMain> sheetData = parseSheet(sheet, company, username);
                importDataList.addAll(sheetData);
            }

            return batchSave(importDataList, updateSupport);
        }
    }

    /**
     * 新增研发人员工时汇总主
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 结果
     */
    @Override
    public int insertRdEmpHoursMain(RdEmpHoursMain rdEmpHoursMain)
    {
        rdEmpHoursMain.setCreateTime(DateUtils.getNowDate());
        return rdEmpHoursMainMapper.insertRdEmpHoursMain(rdEmpHoursMain);
    }

    /**
     * 修改研发人员工时汇总主
     * 
     * @param rdEmpHoursMain 研发人员工时汇总主
     * @return 结果
     */
    @Override
    public int updateRdEmpHoursMain(RdEmpHoursMain rdEmpHoursMain)
    {
        rdEmpHoursMain.setUpdateTime(DateUtils.getNowDate());
        return rdEmpHoursMainMapper.updateRdEmpHoursMain(rdEmpHoursMain);
    }

    /**
     * 批量删除研发人员工时汇总主
     * 
     * @param ids 需要删除的研发人员工时汇总主主键
     * @return 结果
     */
    @Override
    public int deleteRdEmpHoursMainByIds(String[] ids)
    {
        return rdEmpHoursMainMapper.deleteRdEmpHoursMainByIds(ids);
    }

    /**
     * 删除研发人员工时汇总主信息
     * 
     * @param id 研发人员工时汇总主主键
     * @return 结果
     */
    @Override
    public int deleteRdEmpHoursMainById(String id)
    {
        return rdEmpHoursMainMapper.deleteRdEmpHoursMainById(id);
    }

    /**
     * 解析单个工作表数据
     */
    private List<RdEmpHoursMain> parseSheet(Sheet sheet, String company,String username) {
        List<RdEmpHoursMain> dataList = new ArrayList<>();

        // 1. 从标题行提取年月（标题行通常为第0行）
        Row titleRow = sheet.getRow(0);
        if (titleRow == null) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]缺少标题行（XXXX年X月研发人员工时分配表）");
        }
        Cell titleCell = titleRow.getCell(0);
        String title = getCellValue(titleCell);
        // 正则匹配“XXXX年X月”（如“2025年7月”）
        Pattern pattern = Pattern.compile("(\\d+)年(\\d+)月");
        Matcher matcher = pattern.matcher(title);
        Long workYear = null;
        Long workMonth = null;
        if (matcher.find()) {
            workYear = Long.parseLong(matcher.group(1)); // 提取年份（如2025）
            workMonth = Long.parseLong(matcher.group(2)); // 提取月份（如7）
        } else {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]标题格式错误，需包含“XXXX年X月”（如2025年7月）");
        }

        // 2. 确定关键列索引（研发人员姓名、月工作时间）
        Row headerRow = sheet.getRow(2); // 表头行（第三行，0-based索引2）
        if (headerRow == null) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]缺少表头行");
        }

        int nameColIndex = -1; // 研发人员姓名列索引
        int monthHoursColIndex = -1; // 月工作时间列索引
        int lastCellNum = headerRow.getLastCellNum();

        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = headerRow.getCell(i);
            String cellValue = getCellValue(cell);
            if ("研发人员姓名".equals(cellValue)) {
                nameColIndex = i;
            } else if ("月工作时间".equals(cellValue)) {
                monthHoursColIndex = i;
            }
        }

        // 校验关键列
        if (nameColIndex == -1 || monthHoursColIndex == -1) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]未找到'研发人员姓名'或'月工作时间'列");
        }
        if (monthHoursColIndex <= nameColIndex) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]'月工作时间'列必须在'研发人员姓名'列之后");
        }

        // 3. 计算日期列数量（姓名列和月工作时间列之间的列数）
        int dayColumnCount = monthHoursColIndex - nameColIndex - 1;
        if (dayColumnCount <= 0) {
            throw new RuntimeException("工作表[" + sheet.getSheetName() + "]日期列数量不能为0");
        }

        // 4. 解析数据行（从表头行下一行开始）
        int firstDataRowNum = 3; // 数据行起始行
        int lastDataRowNum = sheet.getLastRowNum();

        for (int rowNum = firstDataRowNum; rowNum <= lastDataRowNum; rowNum++) {
            Row dataRow = sheet.getRow(rowNum);
            if (dataRow == null) {
                continue;
            }

            RdEmpHoursMain entity = new RdEmpHoursMain();
            // 设置年月（从标题提取）
            entity.setWorkYear(workYear);
            entity.setWorkMonth(workMonth);
            // 其他基础字段
            entity.setCompany(company);
            entity.setCreateBy(username); // 若依框架获取当前登录人

            // 4.1 项目名称（姓名列前一列，索引nameColIndex-1）
            Cell projectCell = dataRow.getCell(nameColIndex - 1);
            entity.setProjectName(getCellValue(projectCell));

            // 4.2 研发人员姓名
            Cell nameCell = dataRow.getCell(nameColIndex);
            entity.setEmpName(getCellValue(nameCell));

            // 4.3 日期列数据（day1 ~ dayN）
            for (int i = 0; i < dayColumnCount; i++) {
                int dayColIndex = nameColIndex + 1 + i; // 日期列起始索引=姓名列+1
                Cell dayCell = dataRow.getCell(dayColIndex);
                String dayValue = getCellValue(dayCell);

                // 处理"休"或空值（设为0），否则转换为数字
                BigDecimal hours = BigDecimal.ZERO;
                if (StringUtils.isNotEmpty(dayValue) && !"休".equals(dayValue.trim())) {
                    try {
                        hours = new BigDecimal(dayValue.trim());
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("工作表[" + sheet.getSheetName() + "]行[" + (rowNum + 1) + "]日期列数据格式错误：" + dayValue);
                    }
                }

                // 反射设置dayN字段（day1对应第1个日期列）
                String fieldName = "day" + (i + 1);
                try {
                    Field field = RdEmpHoursMain.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    field.set(entity, hours);
                } catch (NoSuchFieldException e) {
                    // 忽略实体类中不存在的dayN字段（如超过day31）
                } catch (Exception e) {
                    throw new RuntimeException("设置字段[" + fieldName + "]失败：" + e.getMessage());
                }
            }

            // 4.4 月工作时间
            Cell monthHoursCell = dataRow.getCell(monthHoursColIndex);
            String monthHoursValue = getCellValue(monthHoursCell);
            // 计算日期列数据合计
            BigDecimal daysTotal = BigDecimal.ZERO;
            for (int i = 0; i < dayColumnCount; i++) {
                String fieldName = "day" + (i + 1);
                try {
                    Field field = RdEmpHoursMain.class.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    BigDecimal dayValue = (BigDecimal) field.get(entity);
                    if (dayValue != null) {
                        daysTotal = daysTotal.add(dayValue);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // 忽略不存在的字段或访问异常
                }
            }

        // 判断月工作时间是否为空，为空则设置为日期列数据合计，不为空则进行合计比对判断
            if (StringUtils.isEmpty(monthHoursValue)) {
                entity.setMonthWorkHours(daysTotal);
            } else {
                BigDecimal monthHours = new BigDecimal(monthHoursValue.trim());
                entity.setMonthWorkHours(monthHours);

                // 进行合计比对判断（可以添加误差范围比较）
                BigDecimal difference = monthHours.subtract(daysTotal).abs();
                if (difference.compareTo(new BigDecimal("0.001")) > 0) {
                throw new RuntimeException("工作表[" + sheet.getSheetName() + "]行[" + (rowNum + 1) + "]月工作时间与日期列数据合计不一致：" + monthHoursValue + " != " + daysTotal);
                }
            }

            // 4.5 各项目实际工作时间（月工作时间列后一列）
            Cell projectHoursCell = dataRow.getCell(monthHoursColIndex + 1);
            String projectHoursValue = getCellValue(projectHoursCell);
            if (StringUtils.isNotEmpty(projectHoursValue)) {
                entity.setProjectWorkHours(new BigDecimal(projectHoursValue.trim()));
            }

            // 4.6 从事岗位（实际工作时间列后一列）
            Cell jobPostCell = dataRow.getCell(monthHoursColIndex + 2);
            entity.setJobPost(getCellValue(jobPostCell));

            // 4.7 人员类型（从事岗位列后一列）
            Cell empTypeCell = dataRow.getCell(monthHoursColIndex + 3);
            entity.setEmpType(getCellValue(empTypeCell));

            dataList.add(entity);
        }

        return dataList;
    }
    /**
     * 批量保存数据
     */
    private AjaxResult batchSave(List<RdEmpHoursMain> dataList, boolean updateSupport) {
        if (dataList.isEmpty()) {
            return AjaxResult.success("无数据可导入");
        }

        int successCount = 0;
        int updateCount = 0;
        int skipCount = 0;

        for (RdEmpHoursMain entity : dataList) {
                // 新增数据
                rdEmpHoursMainMapper.insertRdEmpHoursMain(entity);
                successCount++;

        }

        return AjaxResult.success(String.format(
                "导入完成，新增%d条",
                successCount
        ));
    }

    /**
     * 获取单元格文本值（兼容多种单元格类型）
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // 日期类型特殊处理
                } else {
                    return String.valueOf(cell.getNumericCellValue()).trim();
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return getCellValue(cell.getCachedFormulaResultType(), cell);
            default:
                return "";
        }
    }
    // 处理公式单元格
    private String getCellValue(CellType type, Cell cell) {
        if (type == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (type == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        } else if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return "";
        }
    }
}
