package com.swsk.frame.springboot.business.rgyx.ynaqsj.controller;

import com.swsk.frame.springboot.business.rgyx.ynaqsj.service.WorkSubInfoService;
import com.swsk.frame.springboot.core.util.MResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangyongqi
 * @date 2023/7/10
 */
@Api(tags = "地面导入接口")
@Slf4j
@Scope("prototype")
@RestController
@RequestMapping("/workSubInfo")
public class WorkSubInfoController {

    @Autowired
    private WorkSubInfoService workSubInfoService;


    @ApiOperation("根据模板excel批量导入添加")
    @PostMapping("/importPoint")
    public MResult importPoint(@ApiParam(value = "excel文件,只支持Microsoft Excel 97-2003 工作表 (.xls)", required = true) @RequestPart MultipartFile file) {
        return workSubInfoService.importPoint(file);
    }

    @ApiOperation(value = "导入天擎上的飞机数据,只针对开发人员使用该接口")
    @GetMapping("/importPlane")
    public MResult importPlane(@ApiParam(value = "飞机编号,例如:B10E5", defaultValue = "B10E5") @RequestParam("planeNo") String planeNo) {
        return workSubInfoService.importPlane(planeNo);
    }

    @ApiOperation(value = "请求青海省水利厅网站获取水库增蓄数据")
    @GetMapping("/getReservoirWaterInfo")
    public String getReservoirWaterInfo() {
        return workSubInfoService.getReservoirWaterInfo();
    }


    @ApiOperation(value = "从内网获取全国平均土壤相对湿度0-10cm数据")
    @GetMapping("/getRelativeSoil")
    public MResult getRelativeSoil() {
        return workSubInfoService.getRelativeSoil();
    }

    @ApiOperation(value = "从内网获取 逐6小时预报数据、高度与风场数据")
    @GetMapping("/getForecastAndWindData")
    @ApiImplicitParam(name = "type", value = "查询类型，1：逐6小时预报数据，2：高度与风场数据", required = true, dataType = "String", defaultValue = "1")
    public MResult getForecastAndWindData(@RequestParam String type) {
        return workSubInfoService.getForecastAndWindData(type);
    }
}
