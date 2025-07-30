package com.ruoyi.web.controller.util;


import com.ruoyi.startproject.domain.StartProject;
import com.ruoyi.startproject.domain.StartProjectProgress;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WordExportUtil {
    /**
     * 导出项目信息到Word文档
     */
    public static void exportProjectToWord(StartProject project, HttpServletResponse response) {
        try (XWPFDocument document = new XWPFDocument()) {
            // 添加项目基本信息
            addBasicInfoSection(document, project);

            // 添加项目进度表（包含公司名称和创建日期）
            addProgressTableSection(document, project);

            // 添加创新点
            addContentSection(document, "创新点", project.getInnovation());

            // 添加市场定位
            addContentSection(document, "市场定位", project.getMarketPosition());

            // 输出到响应流
            exportToResponse(document, response, project.getProjectName() + "项目报告.docx");
        } catch (IOException e) {
            e.printStackTrace();
            // 可根据RuoYi框架的异常处理机制进行调整
        }
    }

    /**
     * 添加基本信息部分
     */
    private static void addBasicInfoSection(XWPFDocument document, StartProject project) {
        // 标题
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText(project.getProjectName() + " 项目基本信息");
        titleRun.setFontSize(16);
        titleRun.setBold(true);
        titleRun.addBreak();

        // 基本信息表格
        XWPFTable table = document.createTable(7, 2);
        table.setWidth("100%");

        // 设置表格样式
        setTableHeaderStyle(table.getRow(0).getCell(0));
        setTableHeaderStyle(table.getRow(0).getCell(1));

        // 填充表格数据
        setTableCellValue(table, 0, 0, "项目名称");
        setTableCellValue(table, 0, 1, project.getProjectName());

        setTableCellValue(table, 1, 0, "项目编号");
        setTableCellValue(table, 1, 1, project.getProjectNo());

        setTableCellValue(table, 2, 0, "负责人");
        setTableCellValue(table, 2, 1, project.getHeader());

        setTableCellValue(table, 3, 0, "公司名称");
        setTableCellValue(table, 3, 1, project.getCompany());

        setTableCellValue(table, 4, 0, "创建日期");
        setTableCellValue(table, 4, 1, formatDate(project.getCreateDate()));

        setTableCellValue(table, 5, 0, "预算金额");
        setTableCellValue(table, 5, 1, formatAmount(project.getBudgetAmount()) + " 万元");

        setTableCellValue(table, 6, 0, "预估年销售");
        setTableCellValue(table, 6, 1, formatAmount(project.getAnnualSales()) + " 万元");

        // 添加空行
        addEmptyLine(document, 1);
    }

    /**
     * 添加进度表部分（包含公司名称和创建日期）
     */
    private static void addProgressTableSection(XWPFDocument document, StartProject project) {
        // 标题
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText("项目进度表");
        titleRun.setFontSize(14);
        titleRun.setBold(true);
        titleRun.addBreak();

        // 创建表格（表头包含公司名称和创建日期）
        int rowCount = (project.getTableData() != null ? project.getTableData().size() : 0) + 1;
        XWPFTable table = document.createTable(rowCount, 8);
        table.setWidth("100%");

        // 设置表头样式
        XWPFTableRow headerRow = table.getRow(0);
        for (int i = 0; i < 8; i++) {
            setTableHeaderStyle(headerRow.getCell(i));
        }

        // 设置表头内容
        headerRow.getCell(0).setText("序号");
        headerRow.getCell(1).setText("公司名称");
        headerRow.getCell(2).setText("创建日期");
        headerRow.getCell(3).setText("项目");
        headerRow.getCell(4).setText("执行人");
        headerRow.getCell(5).setText("计划开始月份");
        headerRow.getCell(6).setText("计划完成月份");
        headerRow.getCell(7).setText("操作");

        // 填充表格数据
        if (project.getTableData() != null && !project.getTableData().isEmpty()) {
            for (int i = 0; i < project.getTableData().size(); i++) {
                StartProjectProgress progress = project.getTableData().get(i);
                XWPFTableRow row = table.getRow(i + 1);

                row.getCell(0).setText(String.valueOf(i + 1));
                row.getCell(1).setText(project.getCompany());
                row.getCell(2).setText(formatDate(project.getCreateDate()));
                row.getCell(3).setText(progress.getProjectName());
                row.getCell(4).setText(progress.getExecutor());
                row.getCell(5).setText(progress.getStartDate());
                row.getCell(6).setText(progress.getEndDate());
                row.getCell(7).setText("查看详情");

                // 设置单元格样式
                for (int j = 0; j < 8; j++) {
                    setTableCellStyle(row.getCell(j));
                }
            }
        }

        // 添加空行
        addEmptyLine(document, 1);
    }

    /**
     * 添加内容部分
     */
    private static void addContentSection(XWPFDocument document, String title, String content) {
        // 标题
        XWPFParagraph titlePara = document.createParagraph();
        titlePara.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun titleRun = titlePara.createRun();
        titleRun.setText(title);
        titleRun.setFontSize(14);
        titleRun.setBold(true);
        titleRun.addBreak();

        // 内容
        XWPFParagraph contentPara = document.createParagraph();
        contentPara.setAlignment(ParagraphAlignment.LEFT);
        contentPara.setIndentationFirstLine(480); // 首行缩进2个字符
        XWPFRun contentRun = contentPara.createRun();
        contentRun.setText(content);
        contentRun.setFontSize(12);

        // 添加空行
        addEmptyLine(document, 1);
    }

    /**
     * 设置表格表头样式
     */
    private static void setTableHeaderStyle(XWPFTableCell cell) {
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTShd shd = tcPr.addNewShd();
        shd.setFill("D3D3D3"); // 灰色背景
        shd.setVal(STShd.Enum.forString("clear"));

        XWPFParagraph para = cell.getParagraphs().get(0);
        para.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = para.createRun();
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("宋体");
    }

    /**
     * 设置表格单元格样式
     */
    private static void setTableCellStyle(XWPFTableCell cell) {
        XWPFParagraph para = cell.getParagraphs().get(0);
        para.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = para.createRun();
        run.setFontSize(12);
        run.setFontFamily("宋体");
    }

    /**
     * 设置表格单元格值
     */
    private static void setTableCellValue(XWPFTable table, int row, int col, String value) {
        XWPFTableCell cell = table.getRow(row).getCell(col);
        XWPFParagraph para = cell.getParagraphs().get(0);
        XWPFRun run = para.createRun();
        run.setText(value);
    }

    /**
     * 添加空行
     */
    private static void addEmptyLine(XWPFDocument document, int count) {
        for (int i = 0; i < count; i++) {
            XWPFParagraph para = document.createParagraph();
            para.createRun().addBreak();
        }
    }

    /**
     * 格式化日期
     */
    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    /**
     * 格式化金额
     */
    private static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return amount.divide(new BigDecimal(10000))
                .setScale(2, RoundingMode.HALF_UP)
                .toString();
    }

    /**
     * 导出到响应流
     */
    private static void exportToResponse(XWPFDocument document, HttpServletResponse response, String fileName) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            document.write(baos);
            byte[] content = baos.toByteArray();

            // 设置响应头
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(fileName, "UTF-8"));
            response.setContentLength(content.length);

            // 输出到响应流
            response.getOutputStream().write(content);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
