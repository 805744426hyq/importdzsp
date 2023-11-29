package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huangyongqi
 * @date 2023/8/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkSubInfoExcelDTO implements Serializable {

    private static final long serialVersionUID = 6796079544908385284L;

    @ExcelProperty("work_date_end")
    private String workDateEnd;


    @ExcelProperty("work_before_weather")
    private String weatherAfterHomework;

    @ExcelProperty("work_after_weather")
    private String weatherBeforeWork;

    @ExcelProperty("work_date")
    private String workDate;
    @ExcelProperty("id")
    private String id;

    @ExcelProperty("create_date")
    private String createTime;

    @ExcelProperty("fill_person_name")
    private String fillPerson;


    @ExcelProperty("work_point_name")
    private String jobLocation;

    @ExcelProperty("work_effect")
    private String jobEffect;

    @ExcelProperty("min_max_elevation")
    private String elevation;

    @ExcelProperty("lon")
    private String lon;

    @ExcelProperty("rocket_num")
    private Integer rocketNum;

    @ExcelProperty("work_point_no")
    private String workPointNo;

    @ExcelProperty("work_ware_type")
    private String workWareType;

    @ExcelProperty("work_point_name")
    private String workPointName;


    @ExcelProperty("work_date_begin")
    private String workDateBegin;


    @ExcelProperty("work_type")
    private String jobType;


    @ExcelProperty("create_user_id")
    private String createId;

    @ExcelProperty("min_max_azimuth")
    private String azimuth;

    @ExcelProperty("lat")
    private String lat;

    @ExcelProperty("equip_code")
    private String equipsNo;
}
