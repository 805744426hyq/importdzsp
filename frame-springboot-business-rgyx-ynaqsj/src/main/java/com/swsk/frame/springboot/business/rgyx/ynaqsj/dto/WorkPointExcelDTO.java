package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huangyongqi
 * @date 2023/7/10
 */
@Data
@ApiModel("作业点根据excel模板批量导入添加")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPointExcelDTO implements Serializable {

    private static final long serialVersionUID = 5849031995561447931L;

    @ApiModelProperty("作业点编号")
    @ExcelProperty(index = 0)
    private String workPointNo;

    @ApiModelProperty("作业点名称")
    @ExcelProperty(index = 1)
    private String workPointName;

    @ApiModelProperty("经度")
    @ExcelProperty(index = 2)
    private String lon;

    @ApiModelProperty("纬度")
    @ExcelProperty(index = 3)
    private String lat;

    @ApiModelProperty("海拔高度")
    @ExcelProperty(index = 4)
    private String height;

    @ApiModelProperty("城市名称(全称)")
    @ExcelProperty(index = 5)
    private String belongsCityName;

    @ApiModelProperty("区县名称（全称）")
    @ExcelProperty(index = 6)
    private String belongsCountyName;


}
