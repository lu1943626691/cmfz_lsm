
package com.baizhi.utils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * ExcelUtils 导出excel
 *
 * @author joe
 */
public class ExcelUtils {

    /**
     * 生成一个Excel文件
     *
     * @param title  工作表的名称
     * @param titles 工作表的内容
     */

    public static String writeExcel(String[][] titles, String title,
                                    HttpServletResponse response, Boolean[] titleNumFlags)
            throws IOException {
        String fileName = title + Calendar.getInstance().getTimeInMillis();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String((fileName + ".xls").getBytes(), "UTF-8"));
        // 根据传进来的file对象创建可写入的Excel工作薄
        OutputStream os = response.getOutputStream();
        WritableWorkbook wwb = null;
        try {
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象

            wwb = Workbook.createWorkbook(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wwb != null) {
            // 创建一个可写入的工作表
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet(title, 1);
            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#0.00"); // 设置数字格式
            jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
                    nf); // 设置表单格式
            // 下面开始添加单元格
            for (int row = 0; row < titles.length; row++) {
                // System.out.println(row);
                for (int j = 0; j < titles[row].length; j++) {
                    if (titleNumFlags[j]) {// 当数值型时先转换成double
                        try {
                            double titlesDoubleValue = Double
                                    .parseDouble(titles[row][j]);
                            ws = writeNumberToWs(ws, wcfN, row, j,
                                    titlesDoubleValue);
                        } catch (Exception notnum) {
                            String titlesStringValue = titles[row][j];
                            // 这里需要注意的是，在Excel中，j表示列，row表示行
                            ws = writeStringToWs(ws, row, j, titlesStringValue);
                        }
                    } else {
                        String titlesStringValue = titles[row][j];
                        // 这里需要注意的是，在Excel中，j表示列，row表示行
                        ws = writeStringToWs(ws, row, j, titlesStringValue);
                    }
                }
            }

            try {
                // 从内存中写入文件中
                wwb.write();
                // 关闭资源，释放内存
                wwb.close();
                os.flush();
                os.close();
            } catch (IOException | WriteException e) {
                //e.printStackTrace();
            }
        }
        return fileName;
    }

    private static WritableSheet writeNumberToWs(WritableSheet ws,
                                                 jxl.write.WritableCellFormat wcfN, int row, int j,
                                                 double titlesDoubleValue) {
        jxl.write.Number labelNF = new jxl.write.Number(j, row,
                titlesDoubleValue, wcfN); // 格式化数值
        try {
            ws.addCell(labelNF);
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        return ws;
    }

    private static WritableSheet writeStringToWs(WritableSheet ws, int row,
                                                 int j, String titlesStringValue) {
        Label labelC = new Label(j, row, titlesStringValue);
        try {
            // 将生成的单元格添加到工作表中
            ws.addCell(labelC);
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return ws;
    }

    public static String[][] changeToArray(ArrayList<String[]> contentsArrayList) {
        String[][] contents = new String[contentsArrayList.size()][];
        for (int i = 0; i < contentsArrayList.size(); i++) {
            contents[i] = contentsArrayList.get(i);
        }
        return contents;
    }

}
