package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huangyongqi
 * @date 2023/9/4
 */
@Data
@ApiModel("土壤相对湿度数据,返回数据")
@AllArgsConstructor
@NoArgsConstructor
public class RealtiveSoilRes implements Serializable {
    private static final long serialVersionUID = -7589936261648090504L;

    private static final String TEXT = "日 全国6.25km日平均土壤相对湿度(00-10cm)";
    private String fileName;
    private String filePath;
    private String obs_time;
    private String text;
    private String title;

    public RealtiveSoilRes(RealtiveSoil soil, String obs_time) {
        RealtiveSoil.DataDTO dto = soil.getData().get(0);
        fileName = dto.getFile_NAME();
        filePath = dto.getFile_URL();
        title = obs_time;
        this.obs_time = obs_time;
        text = obs_time.substring(8, 10) + TEXT;


    }
}
