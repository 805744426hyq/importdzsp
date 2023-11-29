package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SDP请求参数签名工具类(使用MD5算法进行签名)
 * 大数据云平台元数据接口使用此数据签名工具类
 */
public class SdpSignUtil {

    public static String getSignString(Map<String, Object> params) {
        return getSignString(params, null);
    }

    /**
     * 生成sign值
     *
     * @param params 参数集合
     * @param salt   盐值
     * @return
     */
    public static String getSignString(Map<String, Object> params, String salt) {
        List<String> paramList = new ArrayList<>();
        //参数获取
        String equal = "=";
        for (String key : params.keySet()) {
            paramList.add(key + equal + params.get(key));
        }

        if (StrUtil.isNotBlank(salt)) {
            paramList.add(salt + equal);
        }

        Collections.sort(paramList);
        String paramStr = StringUtils.join(paramList, "&");
        //MD5并转换为大写,得到sign值
        return new MD5().digestHex(paramStr).toUpperCase();
    }


}
