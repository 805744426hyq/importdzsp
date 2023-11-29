package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangyongqi
 * @date 2023/9/4
 */
@Data
@ApiModel("土壤相对湿度数据")
@AllArgsConstructor
@NoArgsConstructor
public class RealtiveSoil implements Serializable {

    private static final long serialVersionUID = -3846876084879863320L;


    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {


        private String file_URL;
        private String file_NAME;
    }
}
