package com.swsk.frame.springboot.business.rgyx.ynaqsj.service;

import com.swsk.frame.springboot.core.hibernate.service.IBaseService;
import com.swsk.frame.springboot.core.util.MResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huangyongqi
 * @date 2023/7/10
 */
public interface WorkSubInfoService extends IBaseService {


    /**
     * 根据模板excel批量导入添加
     *
     * @param file
     * @return
     */
    MResult importPoint(MultipartFile file);


    /**
     * 导入天擎上的飞机数据
     *
     * @param planeNo
     * @return
     */
    MResult importPlane(String planeNo);

    /**
     * 请求青海省水利厅网站获取水库增蓄数据
     *
     * @return
     */
    String getReservoirWaterInfo();

    /**
     * 从内网获取全国平均土壤相对湿度0-10cm数据
     *
     * @return
     */
    MResult getRelativeSoil();

    /**
     * 从内网获取逐6小时预报数据
     *
     * @return
     */

    MResult getForecastAndWindData(String type);


}
