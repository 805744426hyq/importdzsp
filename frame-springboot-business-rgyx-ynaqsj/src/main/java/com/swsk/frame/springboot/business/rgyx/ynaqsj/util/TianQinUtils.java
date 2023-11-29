package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
public class TianQinUtils {

    private static final String tianqinHttpUrl = TianQinProperty.httpUrl;
    private static final String userId = TianQinProperty.userId;
    private static final String pwd = TianQinProperty.pwd;

    /**
     * 获取天擎飞机轨迹url
     *
     * @param planeNo 飞机编号
     * @return
     */
    public static String getTrajectoryUrl(String planeNo) {
        StringBuffer url = new StringBuffer();
        url.append(tianqinHttpUrl).append("/music-ws/api?");
        //读取并设置请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("serviceNodeId", "NMIC_MUSIC_CMADAAS");
        params.put("dataFormat", "json");
        params.put("dataCode", "OTHE_MODI_MOPR_AOI");
        params.put("interfaceId", "getOtherEleByTimeRange");
        params.put("elements", "DATA_ID.Datetime.IYMDHM.RECORD_ID.RYMDHM.UPDATE_TIME.Year.Mon.Day.Hour.Min.Second.Lat.Lon.Alti.TEM.RHU.SPEED.ANGLE.V17001_01.AIRPLANE_NUMBER.AIRPLANE_ORDER,COMMUNICATION_TYPE.CONTENT");
        params.put("timeRange", "[20230724000000,20230824010000]");
        params.put("eleValueRanges", "AIRPLANE_NUMBER:" + planeNo);
        //AK认证
        params.put("userId", userId);
        params.put("pwd", pwd);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", Double.valueOf(new Random().nextDouble()).toString());
        params.put("sign", SdpSignUtil.getSignString(params));
        params.remove("pwd");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (url.length() > 0) {
                url.append("&");
            }

            url.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return url.toString();
    }

}
