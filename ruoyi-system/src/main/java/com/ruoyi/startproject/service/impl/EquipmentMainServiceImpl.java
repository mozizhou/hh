package com.ruoyi.startproject.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.startproject.domain.EquipmentMain;
import com.ruoyi.startproject.domain.EquipmentUsage;
import com.ruoyi.startproject.mapper.EquipmentMainMapper;
import com.ruoyi.startproject.mapper.EquipmentUsageMapper;
import com.ruoyi.startproject.service.IEquipmentMainService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 设备主Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class EquipmentMainServiceImpl implements IEquipmentMainService
{
    @Autowired
    private EquipmentMainMapper equipmentMainMapper;
    @Autowired
    private EquipmentUsageMapper equipmentUsageMapper;


    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");


    @Transactional
    public AjaxResult importEquipmentUsage(MultipartFile file, boolean updateSupport,String company) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) { // 处理xlsx格式，如需处理xls请改用HSSFWorkbook

            List<EquipmentMain> importDataList = new ArrayList<>();
            int sheetCount = workbook.getNumberOfSheets();

            // 遍历所有工作表
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet == null) {
                    continue;
                }
                // 解析当前工作表数据
                List<EquipmentMain> equipmentMains = parseMainInfo(sheet, company);
                importDataList.addAll(equipmentMains);
            }

            return batchSave(importDataList, updateSupport);
        }
    }

    /**
     * 解析主表信息（设备基本信息）
     */
    private List<EquipmentMain> parseMainInfo(Sheet sheet,String company) {
        int lastRowNum = sheet.getLastRowNum();
        String sheetName = sheet.getSheetName();
        //主表记录
        List<Integer> mainRowNumbers = new ArrayList<>();
        List<EquipmentMain> result = new ArrayList<EquipmentMain>();
        // 收集所有主表记录的行号
        for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null || isBlankRow(row)) {
                continue;
            }

            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.STRING) {
                String cellValue = cell.getStringCellValue().trim();
                if (cellValue.contains("年设备") && cellValue.contains("月使用记录")) {
                    mainRowNumbers.add(rowNum);
                }
            }
        }
        //将主表记录从小到大排序
        mainRowNumbers.sort(Integer::compareTo);
        // 按照主表行号顺序处理每条主表记录及其子表数据
        for (int i = 0; i < mainRowNumbers.size(); i++) {
            int currentMainRow = mainRowNumbers.get(i);
            int nextMainRow = (i < mainRowNumbers.size() - 1) ? mainRowNumbers.get(i + 1) : lastRowNum ;

            // 解析主表记录
            EquipmentMain mainRecord = parseMainInfo1(sheet, currentMainRow, company);
            // 解析子表记录（主表行下一行到下一个主表行前）
            List<EquipmentUsage> subRecords = parseUsageData(sheet, currentMainRow + 4, nextMainRow - 1);
            mainRecord.setTableData(subRecords);

            result.add(mainRecord);
        }

        return result;
    }

    private EquipmentMain parseMainInfo1(Sheet sheet, int mainRow,String company) {
        Row row = sheet.getRow(mainRow);
        Row rowOther = sheet.getRow(mainRow+1);
        if (row == null || isBlankRow(row)) {
            return null;
        }

        EquipmentMain main = new EquipmentMain();
        String sheetName = getCellStringValue(row.getCell(0));

        // 正则表达式：匹配“XX年”形式，并提取前后的年份和月份
        java.util.regex.Pattern yearPattern = java.util.regex.Pattern.compile("(\\d{1,2})\\s*年");
        java.util.regex.Pattern monthPattern = java.util.regex.Pattern.compile("\\b(\\d{1,2})\\b");

        java.util.regex.Matcher yearMatcher = yearPattern.matcher(sheetName);
        java.util.regex.Matcher monthMatcher = monthPattern.matcher(sheetName);
        Long year = null;
        Long month = null;
        if (yearMatcher.find()) {
            try {
                year = Long.parseLong(yearMatcher.group(1));
            } catch (NumberFormatException ignored) {}
        }

        if (monthMatcher.find()) {
            try {
                month = Long.parseLong(monthMatcher.group(1));
            } catch (NumberFormatException ignored) {}
        }
        if (year == null || month == null){
            throw new RuntimeException("无法解析表头年份与月份");
        }
        main.setYear(year);
        main.setMouth(month);
        main.setCompany(company); // 公司名称
        String equipmentName = getCellValue(rowOther, 0);
        main.setEquipmentName(StringUtils.removePrefixAndWhitespace(equipmentName, "设备名称"));

        String specification = getCellValue(rowOther, 3);
        main.setSpecification(StringUtils.removePrefixAndWhitespace(specification, "型号/规格"));

        String equipmentno = getCellValue(rowOther, 5);
        main.setEquipmentNo(StringUtils.removePrefixAndWhitespace(equipmentno, "设备编号"));

        String place = getCellValue(rowOther, 9);
        main.setPlace(StringUtils.removePrefixAndWhitespace(place, "放置地点"));

        return main;
    }


    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    private Double getCellDoubleValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        cell.setCellType(CellType.NUMERIC);
        return cell.getNumericCellValue();
    }


    /**
     * 解析子表使用记录数据
     */
    private List<EquipmentUsage> parseUsageData(Sheet sheet, int startRow, int endRow) {
        List<EquipmentUsage> subTableList = new ArrayList<>();

        for (int rowNum = startRow; rowNum <= endRow && rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            // 跳过全行为0或空的记录
            if (isBlankRow(row)) {
                continue;
            }
            EquipmentUsage usage = new EquipmentUsage();
            String day = getCellValue(row, 0);
            Date startTimeAm = (Date) getDataCellValue(row, 1);
            Date stopTimeAm = (Date) getDataCellValue(row, 2);
            String actualTimeAm = getCellValue(row, 3);
            Date startTimePm = (Date) getDataCellValue(row, 4);
            Date stopTimePm = (Date) getDataCellValue(row, 5);
            String actualTimePm = getCellValue(row, 6);
            String operation = getCellValue(row, 7);
            String department = getCellValue(row, 8);
            String users = getCellValue(row, 9);
            String mark = getCellValue(row, 10);
            if (day != null&&!day.equals("")){
                usage.setDay(Long.parseLong(day));
            }else{
                continue;
            }
            if (startTimeAm != null){
                usage.setStartTimeAm(startTimeAm);
            }
            if (stopTimeAm != null){
                usage.setStopTimeAm(stopTimeAm);
            }
            if (actualTimeAm != null&&!actualTimeAm.equals("")){
                usage.setActualTimeAm(Float.parseFloat(actualTimeAm));
            }
            if (startTimePm != null){
                usage.setStartTimePm(startTimePm);
            }
            if (stopTimePm != null){
                usage.setStopTimePm(stopTimePm);
            }
            if (actualTimePm != null&&!actualTimePm.equals("")){
                usage.setActualTimePm(Float.parseFloat(actualTimePm));
            }
            if (operation != null&&!operation.equals("")){
                usage.setOperation(operation);
            }
            if (department != null&&!department.equals("")){
                usage.setDepartment(department);
            }
            if (users != null&&!users.equals("")){
                usage.setUsers(users);
            }
            if (mark != null&&!mark.equals("")){
                usage.setMark(mark);
            }

            subTableList.add(usage);


        }
        return subTableList;
    }

    /**
     * 批量保存数据
     */
    private AjaxResult batchSave(List<EquipmentMain> dataList, boolean updateSupport) {
        if (dataList == null || dataList.isEmpty()) {
            return AjaxResult.error("导入数据为空");
        }

        int successCount = 0;
        int failCount = 0;
        StringBuilder failMsg = new StringBuilder();

        for (EquipmentMain mainEntity : dataList) {
            try {
                // 保存主表
                if (mainEntity.getId() == null) {
                    equipmentMainMapper.insertEquipmentMain(mainEntity);
                } else if (updateSupport) {
                    equipmentMainMapper.updateEquipmentMain(mainEntity);
                }

                Long mainId = mainEntity.getId();
                if (mainId == null) {
                    throw new Exception("主表ID为空");
                }

                // 先删除旧数据（如果是更新）
                if (updateSupport && mainEntity.getId() != null) {
                    equipmentUsageMapper.deleteEquipmentUsageByMainId(mainId);
                }

                // 保存子表
                List<EquipmentUsage> usageList = mainEntity.getTableData();
                if (usageList != null) {
                    for (EquipmentUsage usage : usageList) {
                        usage.setMainId(mainId);
                        equipmentUsageMapper.insertEquipmentUsage(usage);
                    }
                }

                successCount++;
            } catch (Exception e) {
                failCount++;
                String msg = "第" + (successCount + failCount) + "条数据导入失败，原因：" + e.getMessage();
                failMsg.append(msg).append("<br>");
            }
        }

        if (failCount > 0) {
            failMsg.insert(0, "【导入结果】成功" + successCount + "条，失败" + failCount + "条，如下：<br>");
        } else {
            failMsg.insert(0, "【导入结果】全部" + successCount + "条数据导入成功！");
        }

        return AjaxResult.success(failMsg.toString());
    }

    /**
     * 解析时间字符串
     */
    private Date parseTime(String timeStr) {
        if (StringUtils.isEmpty(timeStr)) {
            return null;
        }
        try {
            return TIME_FORMAT.parse(timeStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取单元格值
     */
    private String getCellValue(Row row, int colIndex) {
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return null;
        }

        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
    private  Object getDataCellValue(Row row, int colIndex) {
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            return null;
        }
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    // 对于 [h]:mm:ss 格式，POI 会按日期处理，但实际是时长，这里特殊处理
                    // 计算时长的分钟数等，可根据需求调整返回格式
//                    long totalSeconds = (long) (cell.getNumericCellValue() * 24 * 60 * 60);
//                    long hours = totalSeconds / (60 * 60);
//                    long minutes = (totalSeconds % (60 * 60)) / 60;
//                    long seconds = totalSeconds % 60;
                    return date;
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                try {
                    return getDateFormatCellValue(cell.getCachedFormulaResultType(), cell);
                } catch (Exception e) {
                    return "公式计算错误";
                }
            default:
                return null;
        }
    }
    private static Object getDateFormatCellValue(CellType cellType, Cell cell) {
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    return df.format(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return "";
        }
    }

    /**
     * 判断行是否为空或全为0
     */
    private boolean isBlankRow(Row row) {
        if (row == null) {
            return true;
        }

        for (int c = 0; c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && !"0".equals(getCellValue(row, c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 查询设备主
     * 
     * @param id 设备主主键
     * @return 设备主
     */
    @Override
    public EquipmentMain selectEquipmentMainById(Long id)
    {
        EquipmentMain equipmentMain = equipmentMainMapper.selectEquipmentMainById(id);
        List<EquipmentUsage> equipmentUsages = equipmentUsageMapper.selectEquipmentUsageListByID(id);
        equipmentMain.setTableData(equipmentUsages);
        return equipmentMain;
    }

    /**
     * 查询设备主列表
     * 
     * @param equipmentMain 设备主
     * @return 设备主
     */
    @Override
    public List<EquipmentMain> selectEquipmentMainList(EquipmentMain equipmentMain)
    {
        return equipmentMainMapper.selectEquipmentMainList(equipmentMain);
    }

    /**
     * 新增设备主
     * 
     * @param equipmentMain 设备主
     * @return 结果
     */
    @Override
    public int insertEquipmentMain(EquipmentMain equipmentMain)
    {

        // 执行插入操作（此时会自动填充 equipmentMain.id）
        equipmentMainMapper.insertEquipmentMain(equipmentMain);

        // 获取自增ID（此时 equipmentMain.getId() 已有值）
        Long generatedId = equipmentMain.getId();

        if (equipmentMain.getTableData() != null && !equipmentMain.getTableData().isEmpty()) {
            for (EquipmentUsage usage : equipmentMain.getTableData()) {
                // 使用已获取的自增ID
                usage.setMainId(generatedId);
                if (usage.getId() == null) {
                    equipmentUsageMapper.insertEquipmentUsage(usage);
                } else {
                    equipmentUsageMapper.updateEquipmentUsage(usage);
                }
            }
        }
        return 1; // 或返回实际影响行数
    }

    /**
     * 修改设备主
     * 
     * @param equipmentMain 设备主
     * @return 结果
     */
    @Override
    public int updateEquipmentMain(EquipmentMain equipmentMain)
    {
        if (equipmentMain.getTableData() != null&&equipmentMain.getTableData().size() > 0){
            for (EquipmentUsage usage : equipmentMain.getTableData()){
                usage.setMainId(equipmentMain.getId());
                if (usage.getId() == null){
                    equipmentUsageMapper.insertEquipmentUsage( usage);
                }else{
                    equipmentUsageMapper.updateEquipmentUsage(usage);
                }
            }
        }
        return equipmentMainMapper.updateEquipmentMain(equipmentMain);
    }

    /**
     * 批量删除设备主
     * 
     * @param ids 需要删除的设备主主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentMainByIds(Long[] ids)
    {
        return equipmentMainMapper.deleteEquipmentMainByIds(ids);
    }

    /**
     * 删除设备主信息
     * 
     * @param id 设备主主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentMainById(Long id)
    {
        return equipmentMainMapper.deleteEquipmentMainById(id);
    }
}
