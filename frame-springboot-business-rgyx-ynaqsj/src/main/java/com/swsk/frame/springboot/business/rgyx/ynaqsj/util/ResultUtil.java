package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;

import com.swsk.frame.springboot.core.util.MResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangyongqi
 * @date 2023/4/7
 */

@Slf4j
public class ResultUtil {


    private static final String MSG = "ok";


    /**
     * 加一个成功的返回
     *
     * @param data 参数
     * @return
     */
    public static MResult ok(Object data) {
        MResult mResult = new MResult(MResult.SUCCESS, MSG);
        mResult.setData(data);
        return mResult;
    }

    public static MResult msg(String message) {
        return new MResult(MResult.SUCCESS, message);
    }

    /**
     * 加一个失败的返回
     *
     * @param message 返回信息
     * @return
     */
    public static MResult error(String message) {
        return new MResult(MResult.ERROR, message);
    }


}
