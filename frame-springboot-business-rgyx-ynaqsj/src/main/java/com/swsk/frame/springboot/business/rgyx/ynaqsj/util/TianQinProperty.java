package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 天擎配置文件值注入
 */
@Component
public class TianQinProperty {

    /**
     * 天擎服务接口url
     */
    public static String httpUrl;
    /**
     * 天擎服务接口账号
     */
    public static String userId;
    /**
     * 天擎服务接口密码
     */
    public static String pwd;

    @Value("${tianQin.httpUrl}")
    public void setHttpUrl(String httpUrl) {
        TianQinProperty.httpUrl = httpUrl;
    }

    @Value("${tianQin.userId}")
    public void setUserId(String userId) {
        TianQinProperty.userId = userId;
    }

    @Value("${tianQin.pwd}")
    public void setPwd(String pwd) {
        TianQinProperty.pwd = pwd;
    }
}
