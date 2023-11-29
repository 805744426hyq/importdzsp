package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huangyongqi
 * @date 2023/9/11
 */
@Data
@ApiModel("")
@AllArgsConstructor
@NoArgsConstructor
public class MResultValue implements Serializable {

    private static final long serialVersionUID = 1443396185364739230L;

    private String key;
    private String value;
}
