package com.ruoyi.startproject.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.startproject.domain.EquipmentMain;
import com.ruoyi.startproject.domain.EquipmentUsage;
import com.ruoyi.startproject.domain.MaterialSummary;
import com.ruoyi.startproject.mapper.MaterialSummaryMapper;
import com.ruoyi.startproject.service.IMaterialSummaryService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 材料领用Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-18
 */
@Service
public class MaterialSummaryServiceImpl implements IMaterialSummaryService
{
    @Autowired
    private MaterialSummaryMapper materialSummaryMapper;

    /**
     * 查询材料领用
     * 
     * @param id 材料领用主键
     * @return 材料领用
     */
    @Override
    public MaterialSummary selectMaterialSummaryById(Long id)
    {
        return materialSummaryMapper.selectMaterialSummaryById(id);
    }

    /**
     * 查询材料领用列表
     * 
     * @param materialSummary 材料领用
     * @return 材料领用
     */
    @Override
    public List<MaterialSummary> selectMaterialSummaryList(MaterialSummary materialSummary)
    {
        return materialSummaryMapper.selectMaterialSummaryList(materialSummary);
    }

    @Override
    public Map<String, List<MaterialSummary>> view(MaterialSummary materialSummary) {
        List<MaterialSummary> materialSummaries = materialSummaryMapper.selectMaterialSummaryList(materialSummary);
        Map<String, List<MaterialSummary>> collect = materialSummaries.stream().collect(Collectors.groupingBy(MaterialSummary::getProjectName));
        return collect;
    }

    @Override
    public List<MaterialSummary> selectMaterialSummaryListByorders(MaterialSummary materialSummary) {
        //什么查询条件都没有的则查询所有(限制公司,只需要公司，年，总数，总金额四个字段)
        if (materialSummary.getYear()==null||materialSummary.getYear().equals("")){
            return materialSummaryMapper.selectMaterialSummaryListAll(materialSummary);
        }
        //如果有公司和年份条件查询且没有月份查询的
        if ((materialSummary.getYear()!=null&&!materialSummary.getYear().equals(""))&&(materialSummary.getMouth()==null||materialSummary.getMouth().equals(""))){
            return materialSummaryMapper.selectMaterialSummaryListByYear(materialSummary);

        }
        //有公司和年份条件查询且有月份查询的
        if ((materialSummary.getYear()!=null&&!materialSummary.getYear().equals(""))&&(materialSummary.getMouth()!=null&&!materialSummary.getMouth().equals(""))&&(materialSummary.getProjectName()==null||materialSummary.getProjectName().equals(""))){
            return  materialSummaryMapper.selectMaterialSummaryListByMouth(materialSummary);
        }
        List<MaterialSummary> materialSummaries = materialSummaryMapper.selectMaterialSummaryList(materialSummary);
        return materialSummaries;
    }

//    @Transactional( rollbackFor = Exception.class)
//    @Override
//    public String importMaterialSummary(List<MaterialSummary> list, boolean updateSupport,String company) {
//        if (StringUtils.isNull(list) || list.size() == 0) {
//            throw new ServiceException("导入数据不能为空！");
//        }
//        int successNum = 0;
//        int failureNum = 0;
//
//        //todo  批量导入
//        String operName = SecurityUtils.getUsername();
//        String message = "导入成功";
//        for (MaterialSummary materialSummary : list) {
//            try {
//                materialSummary.setCreateBy(operName);
//                materialSummary.setCompany(company);
//                materialSummaryMapper.insertMaterialSummary(materialSummary);
//                successNum++;
//            }catch (Exception e){
//                failureNum++;
//            }
//        }
//        return message+successNum+"条";
//    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importMaterialSummary(MultipartFile file, boolean updateSupport, String company) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) { // 处理xlsx格式，如需处理xls请改用HSSFWorkbook

            List<MaterialSummary> importDataList = new ArrayList<>();
            int sheetCount = workbook.getNumberOfSheets();

            // 遍历所有工作表
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet == null) {
                    continue;
                }
                // 解析当前工作表数据
                List<MaterialSummary> equipmentMains = parseAll(sheet, company);
                importDataList.addAll(equipmentMains);
            }

            return batchSave(importDataList, updateSupport);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importMaterialSummaryBYmonth(MultipartFile file, boolean updateSupport, String company) throws IOException {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) { // 处理xlsx格式，如需处理xls请改用HSSFWorkbook

            List<MaterialSummary> importDataList = new ArrayList<>();
            int sheetCount = workbook.getNumberOfSheets();

            // 遍历所有工作表
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                if (sheet == null) {
                    continue;
                }
                // 解析当前工作表数据
                List<MaterialSummary> equipmentMains = parseAllBymonth(sheet, company);
                importDataList.addAll(equipmentMains);
            }

            return batchSave(importDataList, updateSupport);
        }
    }

    private List<MaterialSummary> parseAll(Sheet sheet, String company) {
        int lastRowNum = sheet.getLastRowNum();
        List<MaterialSummary> result = new ArrayList<MaterialSummary>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            // 跳过全行为0或空的记录
            if (isBlankRow(row)) {
                continue;
            }
            MaterialSummary usage = new MaterialSummary();

            Cell dateCell = row.getCell(0);
            String year = null;
            String month = null;
            String day = null;


            if (dateCell != null) {
                switch (dateCell.getCellType()) {
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(dateCell)) {
                            // 情况1: 是日期格式
                            Date date = dateCell.getDateCellValue();
                            year = new SimpleDateFormat("yyyy").format(date);
                            month = new SimpleDateFormat("MM").format(date);
                            day = new SimpleDateFormat("dd").format(date);
                        } else {
                            // 情况2: 是数值，但不是日期格式，尝试解析为Excel日期序列号
                            double serial = dateCell.getNumericCellValue();
                            try {
                                Date date = DateUtil.getJavaDate(serial);
                                year = new SimpleDateFormat("yyyy").format(date);
                                month = new SimpleDateFormat("MM").format(date);
                                day = new SimpleDateFormat("dd").format(date);
                            } catch (Exception ignored) {
                                // 不是一个有效的日期序列号，跳过
                            }
                        }
                        break;
                    case STRING:
                        // 情况3: 是字符串，可能是自定义格式 "2025年6月18日"
                        String dateString = dataFormatter.formatCellValue(dateCell);
                        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
                        java.util.regex.Matcher matcher = pattern.matcher(dateString);
                        if (matcher.find()) {
                            year = matcher.group(1);
                            month = matcher.group(2);
                            day = matcher.group(3);
                            break;
                        }

                        // 情况4: 是字符串，但内容是 Excel 日期序列号（如 "44562"）
                        if (StringUtils.isNumeric(dateString.trim())) {
                            try {
                                double serial = Double.parseDouble(dateString.trim());
                                Date date = DateUtil.getJavaDate(serial);
                                year = new SimpleDateFormat("yyyy").format(date);
                                month = new SimpleDateFormat("MM").format(date);
                                day = new SimpleDateFormat("dd").format(date);
                            } catch (Exception ignored) {
                                // 不是一个有效的日期序列号，跳过
                            }
                        }
                        break;
                    default:
                        // 其他类型忽略
                        break;
                }
            }
//            Date dates = (Date) getDataCellValue(row, 0);
//            String year = null;
//            String mouth = null;
//            String day = null;
//            if (dates != null){
//                 year = Integer.valueOf(dates.getYear()).toString();
//                 mouth = Integer.valueOf(dates.getMonth()).toString();
//                 day = Integer.valueOf(dates.getDate()).toString();
//            }
            String projectName = getCellValue(row, 1);
            String name = getCellValue(row, 2);
            String unit = getCellValue(row, 3);
            BigDecimal total = toBigDecimal(getCellValue(row, 4));
            BigDecimal price = toBigDecimal(getCellValue(row, 5));
            BigDecimal totalAmount = toBigDecimal(getCellValue(row, 6));
            String picker = getCellValue(row, 7);
            if (year!=null&&!year.equals("")){
                usage.setYear(year);
            }
            if (month!=null&&!month.equals("")){
                usage.setMouth(month);
            }
            if (day!=null&&!day.equals("")){
                usage.setDays(day);
            }
            usage.setProjectName(projectName);
            usage.setName(name);
            usage.setUnit(unit);
            usage.setTotal(total);
            usage.setPrice(price);
            usage.setTotalAmount(totalAmount);
            usage.setPicker(picker);
            usage.setCompany(company);

            result.add(usage);
        }

        return result;
    }

    private List<MaterialSummary> parseAllBymonth(Sheet sheet, String company) {
        int lastRowNum = sheet.getLastRowNum();
        List<MaterialSummary> result = new ArrayList<MaterialSummary>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            // 跳过全行为0或空的记录
            if (isBlankRow(row)) {
                continue;
            }

            Cell dateCell = row.getCell(0);
            String year = null;
            String month = null;
//            String day = null;


            if (dateCell != null) {
                switch (dateCell.getCellType()) {
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(dateCell)) {
                            // 情况1: 是日期格式
                            Date date = dateCell.getDateCellValue();
                            year = new SimpleDateFormat("yyyy").format(date);
                            month = new SimpleDateFormat("MM").format(date);
//                            day = new SimpleDateFormat("dd").format(date);
                        } else {
                            // 情况2: 是数值，但不是日期格式，尝试解析为Excel日期序列号
                            double serial = dateCell.getNumericCellValue();
                            try {
                                Date date = DateUtil.getJavaDate(serial);
                                year = new SimpleDateFormat("yyyy").format(date);
                                month = new SimpleDateFormat("MM").format(date);
//                                day = new SimpleDateFormat("dd").format(date);
                            } catch (Exception ignored) {
                                // 不是一个有效的日期序列号，跳过
                            }
                        }
                        break;
                    case STRING:
                        // 情况3: 是字符串，可能是自定义格式 "2025年6月18日"
                        String dateString = dataFormatter.formatCellValue(dateCell);
                        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
                        java.util.regex.Matcher matcher = pattern.matcher(dateString);
                        if (matcher.find()) {
                            year = matcher.group(1);
                            month = matcher.group(2);
//                            day = matcher.group(3);
                            break;
                        }

                        // 情况4: 是字符串，但内容是 Excel 日期序列号（如 "44562"）
                        if (StringUtils.isNumeric(dateString.trim())) {
                            try {
                                double serial = Double.parseDouble(dateString.trim());
                                Date date = DateUtil.getJavaDate(serial);
                                year = new SimpleDateFormat("yyyy").format(date);
                                month = new SimpleDateFormat("MM").format(date);
//                                day = new SimpleDateFormat("dd").format(date);
                            } catch (Exception ignored) {
                                // 不是一个有效的日期序列号，跳过
                            }
                        }
                        break;
                    default:
                        // 其他类型忽略
                        break;
                }
            }
            String projectName = getCellValue(row, 1);
            String name = getCellValue(row, 2);
            String unit = getCellValue(row, 3);
            BigDecimal total = toBigDecimal(getCellValue(row, 4));
//            BigDecimal price = toBigDecimal(getCellValue(row, 5));
            BigDecimal totalAmount = toBigDecimal(getCellValue(row, 5));
            String picker = getCellValue(row, 6);
            BigDecimal price = BigDecimal.ZERO;
            //todo 计算单价 验证total 和total的合理性
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                throw new RuntimeException("第" + rowNum + "行数据异常，数量为0，请检查数据！");
            }else{
                price = totalAmount.divide(total, 5, RoundingMode.HALF_UP);
            }
            if (price.compareTo(BigDecimal.ZERO) == 0){
                throw new RuntimeException("第" + rowNum + "行数据异常，单价为0，请检查数据！");
            }
            // 如果所有关键字段都为空或null，则跳过这条数据
            if ((projectName == null || projectName.trim().isEmpty()) &&
                    (name == null || name.trim().isEmpty()) &&
                    (unit == null || unit.trim().isEmpty()) &&
                    (total == null || total.compareTo(BigDecimal.ZERO) == 0) &&
                    (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) == 0) &&
                    (picker == null || picker.trim().isEmpty())) {
                continue;
            }
            //获取这一行后面的所有数据（后面应当是有几个数据则按数据比列分成几个MaterialSummary对象）
            List<BigDecimal> pointlist = new ArrayList<>();
            List<String> daylist = new ArrayList<>();
            int count=7;
            while (getCellValue(row, count)!=null&&!getCellValue(row, count).equals("")){
                String points = getCellValue(row, count);
                // 单元格有些是按照百分比的单元格格式传过来，那么就是'0.2' 有些则不是百分比单元格格式传过来，接收到的则是类似‘20%‘的字符串，对这两种数据进行检验
                BigDecimal pointsBigDecimal;

                if (points.endsWith("%")) {
                    // 处理百分比格式字符串
                    points = points.substring(0, points.length() - 1).trim();
                    // 校验数据合法性
                    if (!points.matches("^[0-9]+(\\.[0-9]*)?$")) {
                        throw new IllegalArgumentException("百分比格式错误，必须为数字");
                    }
                    // 转换为小数并保留4位小数（用于计算）
                    pointsBigDecimal = new BigDecimal(points).divide(new BigDecimal("100")).setScale(4, RoundingMode.HALF_UP);
                } else {
                    // 处理普通数值格式
                    if (!points.matches("^[0-9]+(\\.[0-9]*)?$")) {
                        throw new IllegalArgumentException("数值格式错误，必须为数字");
                    }
                    pointsBigDecimal = new BigDecimal(points).setScale(4, RoundingMode.HALF_UP);
                }

                count++;
                pointlist.add(pointsBigDecimal);
            }
            // 如果没有输入百分比，则默认为100%
            if (pointlist.size() == 0){
                pointlist.add(new BigDecimal("1"));
            }
            // 计算总和并检查
            BigDecimal sum = pointlist.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

            // 使用精确比较，允许轻微的浮点误差（如0.0001）
            if ((sum.compareTo(BigDecimal.ONE) != 0 &&
                    sum.compareTo(new BigDecimal("1.0001")) > 0) ||(sum.compareTo(BigDecimal.ONE) != 0 && sum.compareTo(new BigDecimal("0.9999")) < 0)) {
                throw new IllegalArgumentException("百分比总和必须为100%，当前总和：" + sum.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
            }
            if (year != null && !year.equals("") && month != null && !month.equals("")) {
                daylist = generateRandomDays(year, month, pointlist.size());
            } else {
                throw new IllegalArgumentException("年份或月份不能为空");
            }
            // 根据占比比例生成MaterialSummary对象，但要保证总金额和总数量一致，单价通过金额和数量计算
//            for (int i = 0; i < pointlist.size(); i++) {
//                //根据占比比例生成MaterialSummary对象,比例是按照百分比生成所以需要缩小,都保留两位小数
//                MaterialSummary usage = new MaterialSummary(null, company, year, month, projectName, name, unit, total,totalAmount, picker, true);
//                usage.setHasChildren(false);
//                usage.setTotal(total.multiply(pointlist.get(i)).setScale(2, RoundingMode.HALF_UP));
//                usage.setTotalAmount(totalAmount.multiply(pointlist.get(i)).setScale(2, RoundingMode.HALF_UP));
//                usage.setPrice(usage.getTotalAmount().divide(usage.getTotal(), 2, RoundingMode.HALF_UP));
//                usage.setProjectName(projectName);
//                usage.setName(name);
//                usage.setUnit(unit);
//                usage.setPicker(picker);
//                usage.setCompany(company);
//                usage.setDays(daylist.get(i));
//                usage.setYear(year);
//                usage.setMouth(month);
//                result.add(usage);
//            }
            // 根据占比比例生成MaterialSummary对象，但要保证总金额和总数量一致，单价通过金额和数量计算
            BigDecimal totalSum = BigDecimal.ZERO;
            BigDecimal amountSum = BigDecimal.ZERO;

            for (int i = 0; i < pointlist.size(); i++) {
                BigDecimal ratio = pointlist.get(i);
                MaterialSummary usage = new MaterialSummary(null, company, year, month, projectName, name, unit, total, totalAmount, picker, true);
                usage.setHasChildren(false);

                if (i == pointlist.size() - 1) {
                    // 最后一项，用于补齐总和差异
                    BigDecimal diffTotal = total.subtract(totalSum);
                    BigDecimal diffAmount = totalAmount.subtract(amountSum);

                    usage.setTotal(diffTotal.setScale(2, RoundingMode.HALF_UP));
                    usage.setTotalAmount(diffAmount.setScale(2, RoundingMode.HALF_UP));
                } else {
                    BigDecimal itemTotal = total.multiply(ratio).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal itemAmount = itemTotal.multiply(price).setScale(2, RoundingMode.HALF_UP);

                    usage.setTotal(itemTotal);
                    usage.setTotalAmount(itemAmount);

                    totalSum = totalSum.add(itemTotal);
                    amountSum = amountSum.add(itemAmount);
                }

                // 增加对总数是否为零的判断，避免除以零的错误
//                if (usage.getTotal().compareTo(BigDecimal.ZERO) != 0) {
//                    usage.setPrice(usage.getTotalAmount().divide(usage.getTotal(), 2, RoundingMode.HALF_UP));
//                } else {
//                    throw new IllegalArgumentException("材料数量不能为零，无法计算单价");
//                }
                usage.setPrice(price);
                usage.setProjectName(projectName);
                usage.setName(name);
                usage.setUnit(unit);
                usage.setPicker(picker);
                usage.setCompany(company);
                usage.setDays(daylist.get(i));
                usage.setYear(year);
                usage.setMouth(month);
                result.add(usage);
            }
        }

        return result;
    }

    public static List<String> generateRandomDays(String yearStr, String monthStr, int count) {
        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);

        // 获取该月的总天数
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        // 创建该月的所有日期的日部分
        List<Integer> days = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            days.add(day);
        }

        // 随机打乱这些天
        Collections.shuffle(days);

        // 取前count个不重复的随机天
        List<Integer> selectedDays = days.subList(0, Math.min(count, daysInMonth));

        // 将整数日转换为两位数的字符串格式
        List<String> formattedDays = new ArrayList<>();
        for (Integer day : selectedDays) {
            formattedDays.add(String.format("%02d", day));
        }

        return formattedDays;
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
    private BigDecimal toBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO; // 或者返回 null，根据业务需求决定
        }
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
     * 批量保存数据
     */
    private AjaxResult batchSave(List<MaterialSummary> dataList, boolean updateSupport) {
        if (dataList == null || dataList.isEmpty()) {
            return AjaxResult.error("导入数据为空");
        }

        int successCount = 0;
        int failCount = 0;
        StringBuilder failMsg = new StringBuilder();

        for (MaterialSummary mainEntity : dataList) {
            try {
                // 保存记录
                materialSummaryMapper.insertMaterialSummary(mainEntity);

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
     * 新增材料领用
     * 
     * @param materialSummary 材料领用
     * @return 结果
     */
    @Override
    public int insertMaterialSummary(MaterialSummary materialSummary)
    {
        return materialSummaryMapper.insertMaterialSummary(materialSummary);
    }

    /**
     * 修改材料领用
     * 
     * @param materialSummary 材料领用
     * @return 结果
     */
    @Override
    public int updateMaterialSummary(MaterialSummary materialSummary)
    {
        return materialSummaryMapper.updateMaterialSummary(materialSummary);
    }

    /**
     * 批量删除材料领用
     * 
     * @param ids 需要删除的材料领用主键
     * @return 结果
     */
    @Override
    public int deleteMaterialSummaryByIds(Long[] ids)
    {
        return materialSummaryMapper.deleteMaterialSummaryByIds(ids);
    }

    /**
     * 删除材料领用信息
     * 
     * @param id 材料领用主键
     * @return 结果
     */
    @Override
    public int deleteMaterialSummaryById(Long id)
    {
        return materialSummaryMapper.deleteMaterialSummaryById(id);
    }
}
