package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author huangyongqi
 * @date 2023/10/12
 */

@Component
public class ExcelDataUtil {


    /**
     * web写出excel
     *
     * @param response 响应对象
     * @param clazz    导出对象 列名的map alisMap.put("belongsCityName", "市");
     * @param rows     写出数据
     * @param fileName 表格名
     * @throws Exception
     */


    public static void exportExcel(HttpServletResponse response, List rows, Class clazz, String fileName) {

        boolean y = true;
        ServletOutputStream out = null;
        try {
            fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(), "UTF-8").replaceAll("\\+", "%20");
            out = response.getOutputStream();
        } catch (Exception e) {

        }

        // 通过工具类创建writer，默认创建xlsx格式
        ExcelWriter writer = ExcelUtil.getWriter(y);
        StyleSet style = writer.getStyleSet();
        // 居中
        style.setAlign(HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle cellStyle = style.getCellStyleForNumber();
        // 自动换行
        cellStyle.setWrapText(y);
        // 数字0位小数点
        cellStyle.setDataFormat((short) 0);

        // 设置列名
        getAlias(clazz, writer);

        response.setContentType(ExcelUtil.XLSX_CONTENT_TYPE);
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment; filename*=utf-8''" + fileName);

        // 只保留别名中的字段值
        writer.setOnlyAlias(y);
        // 自动列宽，此方法必须在指定列数据完全写出后调用才有效
        //    writer.autoSizeColumnAll();
        writer.write(rows, y);
        writer.flush(out, y);
        writer.autoSizeColumnAll();

        writer.close();
        IoUtil.close(out);
    }

    private static void getAlias(Class clazz, ExcelWriter writer) {
        Field[] fields = clazz.getDeclaredFields();
        // @ExcelProperty 取这个注解的value值作为excel的列名
        Class<ExcelProperty> aClass = ExcelProperty.class;
        for (Field field : fields) {
            String name = field.getName();
            // 有打注解才取值
            if (field.isAnnotationPresent(aClass)) {
                ExcelProperty annotation = field.getAnnotation(aClass);
                String[] value = annotation.value();
                writer.addHeaderAlias(name, value[0]);
            }
        }
    }
}

