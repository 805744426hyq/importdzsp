package com.swsk.frame.springboot.business.rgyx.ynaqsj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huangyongqi
 * @date 2023/9/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MResultDTO implements Serializable {

    private static final long serialVersionUID = -6626042506302706317L;

    public static int SUCCESS = 200;
    public static int ERROR = 400;
    public static int USER_NOT_EXIST = 401;
    public static int SERVER_ERROR = 500;
    private int status;
    private String msg = "";
    private List<MResultValue> data = new LinkedList<>();

    public MResultDTO(int status) {
        this.status = status;
    }


}
