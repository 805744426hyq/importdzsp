package com.swsk.frame.springboot.business.rgyx.ynaqsj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.dto.RealtiveSoil;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.dto.RealtiveSoilRes;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.dto.WorkSubInfoExcelDTO;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.entity.WorkSubInfoEntity;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.service.WorkSubInfoService;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.util.ResultUtil;
import com.swsk.frame.springboot.business.rgyx.ynaqsj.util.TianQinUtils;
import com.swsk.frame.springboot.core.hibernate.service.impl.BaseServiceImpl;
import com.swsk.frame.springboot.core.util.ConfigUtil;
import com.swsk.frame.springboot.core.util.DateUtil;
import com.swsk.frame.springboot.core.util.MResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author huangyongqi
 * @date 2023/7/10
 */
@Slf4j
@Service("workSubInfoService")
@Transactional(rollbackOn = Exception.class)
public class WorkSubInfoServiceImpl extends BaseServiceImpl implements WorkSubInfoService {

    public static Map<String, String> weatherMap = new HashMap<>();


    static {
        weatherMap.put("1", "晴");
        weatherMap.put("1.0", "晴");
        weatherMap.put("2", "多云");
        weatherMap.put("2.0", "多云");
        weatherMap.put("3", "阴");
        weatherMap.put("3.0", "阴");
        weatherMap.put("4", "小雨");

        weatherMap.put("5", "阵雨");
        weatherMap.put("6", "中到小雨");
        weatherMap.put("7", "中雨");
        weatherMap.put("8", "大雨");

        weatherMap.put("9", "小雪");
        weatherMap.put("10", "阵雪");
        weatherMap.put("11", "小到中雪");
        weatherMap.put("12", "中雪");

        weatherMap.put("13", "大雪");
        weatherMap.put("14", "雷电");
        weatherMap.put("91", "雨");
        weatherMap.put("92", "雷闪小雨");

    }

    @Value("${spring.profiles.active}")
    public String active;

    /**
     * 根据模板excel批量导入添加
     *
     * @param file
     * @return
     */
    @Override
    public MResult importPoint(MultipartFile file) {


        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<WorkSubInfoEntity> list = new ArrayList<>();

        EasyExcel.read(inputStream, WorkSubInfoExcelDTO.class, new PageReadListener<WorkSubInfoExcelDTO>(dataList -> {
            // 写入数据到集合
            addDataToList(list, dataList);
        })).excelType(ExcelTypeEnum.XLS).sheet().doRead();

        batchSaveOrUpdate(list);

        return ResultUtil.ok(null);
    }

    /**
     * 导入天擎上的飞机数据
     *
     * @param planeNo
     * @return
     */
    @Override
    public MResult importPlane(String planeNo) {
        String trajectoryUrl = TianQinUtils.getTrajectoryUrl(planeNo);
        log.info("请求天擎飞机轨迹的url:{}", trajectoryUrl);
        // 超时时间2分钟
        String res = HttpUtil.get(trajectoryUrl, 2 * 60 * 1000);
        log.info("天擎飞机轨迹返回的参数:{}", res);
        if (StrUtil.equals("prod", active)) {
            String folderName = "F:\\SWSK\\importDzsp\\" + planeNo + ".txt";
            File file = new File(folderName);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileUtil.writeString(res, file, "utf-8");
        }
        // 入库


        return ResultUtil.ok(null);
    }

    /**
     * 请求青海省水利厅网站获取水库增蓄数据
     *
     * @return
     */
    @Override
    public String getReservoirWaterInfo() {
        String url = "http://221.207.11.244:9016/newApi/api/shyj/sl323/rsvr/select-stress-rsvr-by-page";

        String date = DateUtil.getCurrDate("yyyy-MM-dd") + " 08:00";

        JSONObject paramMap = new JSONObject();
        paramMap.put("adcd", "630000000000000");
        paramMap.put("etm", date);
        paramMap.put("stm", date);
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", -1);

        paramMap.put("warnFlag", false);
        paramMap.put("frgrd", "");
        paramMap.put("isOut", "");
        paramMap.put("rvnm", "");
        paramMap.put("stnm", "");

        paramMap.put("wscd", "");
        String jsonString = JSONObject.toJSONString(paramMap);

        return HttpUtil.post(url, jsonString);
    }

    /**
     * 从内网获取全国平均土壤相对湿度0-10cm数据
     *
     * @return
     */
    @Override
    public MResult getRelativeSoil() {
        MResult mResult = new MResult(MResult.ERROR);
        DateTime dateTime = cn.hutool.core.date.DateUtil.yesterday();
        // 时间参数要传昨天
        String time = cn.hutool.core.date.DateUtil.formatDate(dateTime);
        StringBuilder stringBuilder = new StringBuilder("http://10.1.64.154/idata/web/grid4/queryDataCldasDayD?dataCode=NAFP_CLDAS2.0_RT_JPG_D_RSM000010,NAFP_CLDAS2.0_RT_JPG_D_RSM000020,NAFP_CLDAS2.0_RT_JPG_D_RSM000050&time=");
        stringBuilder.append(time).append("&type=3");
        String json = HttpUtil.get(stringBuilder.toString());
        System.out.println(json);
        List<RealtiveSoil> realtiveSoils = JSON.parseArray(json, RealtiveSoil.class);
        // 1是0-10cm
        Optional<RealtiveSoil> first = realtiveSoils.stream().filter(d -> StrUtil.equals("1", d.getType())).findFirst();
        List<RealtiveSoilRes> result = new ArrayList<>(1);
        if (first.isPresent()) {
            RealtiveSoil soil = first.get();
            RealtiveSoilRes res = new RealtiveSoilRes(soil, time);
            result.add(res);
            mResult.setData(result);
            mResult.setStatus(MResult.SUCCESS);
            return mResult;
        }
        mResult.setMsg("暂无数据");
        return mResult;
    }


    @Override
    public MResult getForecastAndWindData(String type) {
        MResult mResult = new MResult(MResult.SUCCESS);
        String today = cn.hutool.core.date.DateUtil.today();
        String regex = "-";
        String todayNow = today.replaceAll(regex, StrUtil.EMPTY);

        String padStr = "0";
        String separator = "/";
        String rootPath = ConfigUtil.getString("project.base.rootPath");

        String directoryStr = rootPath + File.separator + "meteorData" + File.separator + "pctureProducts" + File.separator;
        if (StrUtil.equals("1", type)) {
            long millis = System.currentTimeMillis();
            String[] split = today.split(regex);
            String year = split[0];
            String month = split[1];
            String day = split[2];
            /*
http://10.1.64.146/publish/pub/2023/09/14/STFC/SEVP_NMC_STFC_SFER_ER6T06_ACHN_L88_P9_20230914000000606.JPG?v=1694645866898
http://10.1.64.146/publish/pub/2023/09/14/STFC/SEVP_NMC_STFC_SFER_ER6T12_ACHN_L88_P9_20230914000001206.JPG?v=1694645879931
http://10.1.64.146/publish/pub/2023/09/14/STFC/SEVP_NMC_STFC_SFER_ER6T18_ACHN_L88_P9_20230914000001806.JPG?v=1694645889365
http://10.1.64.146/publish/pub/2023/09/14/STFC/SEVP_NMC_STFC_SFER_ER6T24_ACHN_L88_P9_20230914000002406.JPG?v=1694645901906
             */
            String url = "http://10.1.64.146/publish/pub/";
            // 6开始，每隔6
            for (int i = 6; i <= 24; i += 6) {
                String num = String.valueOf(i);
                // 左边补0到2位长度
                String padPre2 = StrUtil.padPre(num, 2, padStr);
                StringBuilder builder = new StringBuilder(url);

                builder.append(year).append(separator).append(month).append(separator).append(day).
                        append(separator).append("STFC/SEVP_NMC_STFC_SFER_ER6T")
                        .append(padPre2).append("_ACHN_L88_P9_")
                        .append(todayNow).append("00000").append(padPre2).append("06").append(".JPG?v=").append(millis);

                savePic(directoryStr, builder.toString(), "NMC_PRE_6H");
            }
        } else {
            /*
            http://10.1.64.146/picture/ecmwf/2023091312/h500w/h500w500/2023091312_024.png
            http://10.1.64.146/picture/ecmwf/2023091312/h500w/h500w500/2023091312_048.png
             */
            // 0开始，每隔12
            DateTime dateTime = cn.hutool.core.date.DateUtil.parseDate(today);
            DateTime offsetDay = cn.hutool.core.date.DateUtil.offsetDay(dateTime, -1);
            String yesterday = cn.hutool.core.date.DateUtil.formatDate(offsetDay).replaceAll(regex, StrUtil.EMPTY);

            String url = "http://10.1.64.146/picture/ecmwf/";
            for (int i = 0; i <= 240; i += 12) {
                String num = String.valueOf(i);
                String padPre3 = StrUtil.padPre(num, 3, padStr);
                StringBuilder builder = new StringBuilder(url);
                // 500Pa传参是昨天
                builder.append(yesterday).append("12/h500w/h500w500/").append(yesterday).append("12_")
                        .append(padPre3).append(".png");
                savePic(directoryStr, builder.toString(), "WMC-F.0004.0002.O");
            }

        }


        return mResult;
    }


    /**
     * 保存6小时降水预报图片或500Pa风场图片
     *
     * @param directoryStr meteorData/pctureProducts
     * @param url
     * @param dir          文件夹名称
     */
    private void savePic(String directoryStr, String url, String dir) {
        String forecastPath = directoryStr + dir;
        System.out.println(forecastPath + "-------" + url);
        File forecastFile = new File(forecastPath);
        if (!forecastFile.exists()) {
            forecastFile.mkdirs();
        }
        //    HttpUtil.downloadFileFromUrl(url, forecastFile);
    }


    private void addDataToList(List<WorkSubInfoEntity> list, List<WorkSubInfoExcelDTO> dataList) {
        // 写死
        String workPointNo = "63012208";
        String equipsNo = "14de49b6-5363-4af1-b018-5f5ee30e66a0";
        String azimuth = "120";
        String elevation = "60";
        for (WorkSubInfoExcelDTO dto : dataList) {
            WorkSubInfoEntity entity = new WorkSubInfoEntity();
            BeanUtil.copyProperties(dto, entity, true);


            entity.setRealPointNo(dto.getWorkPointNo());
            entity.setWorkPointNo(workPointNo);
            entity.setEquipsNo(equipsNo);
            entity.setAzimuth(azimuth);
            entity.setElevation(elevation);

            try {
                extracted(dto, entity);
            } catch (Exception e) {
                String workPointName = entity.getWorkPointName();
                log.info("作业点为:{}，不含仰角和方位角，-----------", workPointName);
            }

            entity.setJobLocation(dto.getWorkPointName());

            entity.setWeatherBeforeWork(weatherMap.get(dto.getWeatherBeforeWork()));
            entity.setWeatherAfterHomework(weatherMap.get(dto.getWeatherBeforeWork()));
            list.add(entity);
        }
    }

    private void extracted(WorkSubInfoExcelDTO dto, WorkSubInfoEntity entity) {
        String workWareType = dto.getWorkWareType();
        String assignmentStyle = null;
        switch (workWareType) {
            case "0":
                assignmentStyle = "其它";
                break;
            case "1":
                assignmentStyle = "火箭";
                break;
            case "2":
                assignmentStyle = "烟炉";
                break;
            case "3":
                assignmentStyle = "高炮";
                break;
            default:
                break;
        }
        entity.setAssignmentStyle(assignmentStyle);

        String jobType = null;
        String dtoJobType = dto.getJobType();
        switch (dtoJobType) {
            case "1":
                jobType = "增雨(雪)";
                break;
            case "2":
                jobType = "防雹";
                break;
            case "3":
                jobType = "其它";
                break;
            case "4":
                jobType = "消减雨";
                break;
            default:
                break;
        }
        entity.setJobType(jobType);

        String jobeffect = dto.getJobEffect();
        String effect = null;

        switch (jobeffect) {
            case "0":
            case "1.0":
            case "1":
                effect = "不明显";
                break;
            case "2":
                effect = "一般";
                break;
            case "3":
                effect = "较明显";
                break;
            case "4":
                effect = "明显";
                break;
            default:
                break;
        }
        entity.setJobEffect(effect);
    }
}
