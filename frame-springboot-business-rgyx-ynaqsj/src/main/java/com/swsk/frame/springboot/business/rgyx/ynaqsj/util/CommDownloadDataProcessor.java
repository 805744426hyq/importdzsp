//package com.swsk.frame.springboot.business.rgyx.ynaqsj.util;
//
//import cn.hutool.core.io.FileUtil;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.swsk.frame.springboot.core.util.DateUtil;
//import com.swsk.frame.springboot.core.util.MResult;
//import com.swsk.frame.springboot.core.util.SdpSignUtil;
//import com.swsk.frame.springboot.core.util.StringUtil;
//import com.swsk.frame.springboot.project.cdbserver.config.ProjectConfig;
//import com.swsk.frame.springboot.project.cdbserver.core.event.DataSyncEvent;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.bean.EsayMsgBean;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.common.DataSyncConfig;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.datasync.log.util.DataSyncLogUtil;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.datasync.log.util.DeleteExpiredFileUtil;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.entity.WeatherDataSyncInfoEntity;
//import com.swsk.frame.springboot.project.cdbserver.datasyncserver.proc.weathercloudbase.WeatherCloudBaseDataProcessor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.*;
//
///**
// * 大数据云平台基础库通用数据下载接口
// */
//@Slf4j
//@Service("PROC_WEATHER_CLOUDBASE_COMM_DOWNLOAD_DATA")
//@Scope("prototype")
//@Component
//public class CommDownloadDataProcessor extends WeatherCloudBaseDataProcessor {
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public MResult doSync(WeatherDataSyncInfoEntity syncInfo, List<EsayMsgBean> msgList) {
//        MResult result = new MResult(MResult.SUCCESS);
//        result.setData(msgList);
//        String infoMsg = String.format("同步任务进程 %s 开始... %s", syncInfo.getCimissDataCode(), syncInfo.getName());
//        msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
//        log.debug(infoMsg);
//
//        try {
////            int hour = syncInfo.getCopyIgnoreBeforeHourNum();
//            int minute = syncInfo.getCopyIgnoreBeforeHourNum() * 60;
//            String cimissTimeRange = syncInfo.getCimissTimeRange();
//            int maxMinute = Integer.parseInt(cimissTimeRange); //单次请求中时间区间最大范围（单位：分钟）
//            Date currDate = DateUtil.format(DateUtil.format(new Date(), "yyyyMMddHHmm00"), "yyyyMMddHHmmss");
//            currDate = DateUtil.addMinute(currDate, -(LocalDateTime.now().getMinute() % maxMinute));
//            String beginDate = "";
//            String endDate = "";
//            String fillDateBegin = "";
//            String fillDateEnd = "";
//            boolean isFill = false;
//            int fillDateMinute = 0; //补录数据分钟数
//            //忽略列表，包含列表中内容的文件不会被删除
//            String[] delIgnore = null;
//            Map<String, Object> postParams = new HashMap<>();
//            //运行参数
//            String runParams = syncInfo.getRunParams();
//
//            if(!StringUtil.isEmpty(runParams)){
//                //解析运行参数
//                JsonNode paramsNode =  mapper.readTree(runParams);
//
//                if(paramsNode.has("postParams")){
//                    Iterator<Map.Entry<String, JsonNode>> postParams1 = paramsNode.get("postParams").fields();
//
//                    while (postParams1.hasNext()){
//                        Map.Entry<String, JsonNode> entry = postParams1.next();
//                        postParams.put(entry.getKey(), entry.getValue().asText());
//                    }
//                }
//                if (paramsNode.has("delIgnore") && !paramsNode.get("delIgnore").asText().isEmpty()) {
//                    delIgnore = paramsNode.get("delIgnore").asText().split(",");
//                }
//            }
//            //数据补录信息
//            if (ProjectConfig.fillInfo.containsKey(syncInfo.getId())){
//                //获取同步项补录信息
//                Map<String, Object> fillMap = ProjectConfig.fillInfo.get(syncInfo.getId());
//                fillDateBegin = fillMap.get("fillDateBegin").toString();
//                fillDateEnd = fillMap.get("fillDateEnd").toString();
//                isFill = true;
//                //去除同步项补录信息
//                ProjectConfig.fillInfo.remove(syncInfo.getId());
//            }
//
//            if(isFill && !StringUtil.isEmpty(fillDateBegin) && !StringUtil.isEmpty(fillDateEnd)){
//                fillDateMinute = DateUtil.betweenMinute(DateUtil.format(fillDateEnd, "yyyyMMddHHmmss"),
//                        DateUtil.format(fillDateBegin, "yyyyMMddHHmmss"));
//            }
//
//            if(fillDateMinute > 0){
//                minute = fillDateMinute;
//                currDate = DateUtil.format(fillDateEnd.substring(0, 10), "yyyyMMddHH");
//            }
//
//            if(fillDateMinute < maxMinute && minute < maxMinute && !cimissTimeRange.startsWith("9")){
//                beginDate = DateUtil.format(DateUtils.addHours(DateUtils.addMinutes(currDate, -minute), -8), "yyyyMMddHHmm00");
//                endDate = DateUtil.format(DateUtils.addHours(currDate, -8), "yyyyMMddHHmm00");
//                endDate = DateUtil.format(DateUtils.addSeconds(DateUtil.format(endDate, "yyyyMMddHHmmss"), -1), "yyyyMMddHHmmss");
//
//                dataSync(syncInfo, msgList, postParams, beginDate, endDate);
//            }else {
//                String format = "yyyyMMddHHmm00";
//                if (cimissTimeRange.equals("9001")) {
//                    ////cimissTimeRange==9001==逐小时
//                    maxMinute = 60;
//                    format = "yyyyMMddHH0000";
//
//                } else if (cimissTimeRange.equals("9002")) {
//                    ////cimissTimeRange==9002==逐日
//                    maxMinute = 1440;
//                    format = "yyyyMMdd000000";
//                }
//                for (int i = 0; i < minute; ) {
//                    if (i + maxMinute > minute) {
//                        beginDate = DateUtil.format(DateUtils.addHours(DateUtils.addMinutes(currDate, -minute + i), - 8), format);
//                        endDate = DateUtil.format(DateUtils.addHours(currDate, -8), format);
//                        endDate = DateUtil.format(DateUtils.addSeconds(DateUtil.format(endDate, "yyyyMMddHHmmss"), -1), "yyyyMMddHHmmss");
//                    } else {
//                        beginDate = DateUtil.format(DateUtils.addHours(DateUtils.addMinutes(currDate, -minute + i), - 8), format);
//                        endDate = DateUtil.format(DateUtils.addHours(DateUtils.addMinutes(currDate, -minute + (i + maxMinute)), - 8), format);
//                        endDate = DateUtil.format(DateUtils.addSeconds(DateUtil.format(endDate, "yyyyMMddHHmmss"), -1), "yyyyMMddHHmmss");
//                    }
//                    dataSync(syncInfo, msgList, postParams, beginDate, endDate);
//                    System.out.println(beginDate);
//                    System.out.println(endDate);
//                    System.out.println("-------------");
//                    i += maxMinute;
//                }
//            }
//
//            if (syncInfo.getDeleteFilesBeforeDayNum() > 0) {
//                DeleteExpiredFileUtil.delFiles(syncInfo.getTargetPath(), syncInfo, delIgnore, true);
//            }
//        }catch (Exception ex){
//            String errMsg = String.format("进程 %s 已完成，但出现异常！ %s", syncInfo.getCimissDataCode(), ex);
//            log.error(errMsg,ex);
//            msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.ERROR, errMsg));
//            result.setStatus(MResult.ERROR);
//            result.setMsg(errMsg);
//            return result;
//        }finally {
//            infoMsg = String.format("进程 %s 已完成!", syncInfo.getCimissDataCode());
//            msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
//            log.debug(infoMsg);
//        }
//
//        return result;
//    }
//
//    public String dataSync(WeatherDataSyncInfoEntity syncInfo, List<EsayMsgBean> msgList, Map<String, Object> postParams, String beginDate, String endDate) throws Exception {
//        StringBuffer msg = new StringBuffer();
//        //存在是否覆盖
//        int fileExistIsCover = syncInfo.getFileExistIsCover();
//        String targetPath = syncInfo.getTargetPath();
//        String outFilePath = syncInfo.getTargetPath() + File.separator + beginDate + ".txt";
////        //最终的子目录
////        String finalTargetPath = targetPath;
////
////        if (targetPath.indexOf("d{") != -1) {
////            String[] dateStrs = targetPath.split("d\\{");
////
////            for (String dateStr : dateStrs) {
////                if (dateStr.indexOf("}") != -1) {
////                    //路径字符串中占位符
////                    String placeholder = "d{" + dateStr.substring(0, dateStr.indexOf("}") + 1);
////                    //占位符替换
////                    finalTargetPath = finalTargetPath.replace(placeholder,
////                            com.swsk.frame.springboot.project.cdbserver.util.DateUtils.format(cDate, placeholder.substring(2, placeholder.length() - 1)));
////                }
////            }
////
////            if (finalTargetPath.indexOf("d{") != -1) {
////                String infoMsg = String.format("子路径 %s 中日期占位符格式有误！", finalTargetPath);
////                log.error(infoMsg);
////                DataSyncLogUtil.add(syncInfo.getId(), DataSyncLogUtil.LogType.ERROR, infoMsg);
////                msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
////                return "Error";
////            }
////        }
////
////        String finallyTargetPath = finalTargetPath.replaceAll("//", "/");
//
//
//
//        //目标目录是否存在
//        boolean fileIsExist = FileUtil.exist(outFilePath);
//
//        if (fileIsExist) {
//            ////文件是否覆盖
//            if(fileExistIsCover == 2){
//                if(!FileUtil.del(outFilePath)){
//                    String infoMsg = "原文件删除操作失败：" + outFilePath;
//                    msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
//                    return "Error";
//                }else{
//                    String infoMsg = "原文件删除成功：" + outFilePath;
//                    msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
//                }
//            }else{
//                String infoMsg = "文件已存在：" + outFilePath;
//                msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, infoMsg));
//                return "Success";
//            }
//        }
//        int copyConut = 0; //复制文件数量
//        //忽略列表，包含列表中内容的文件不会被删除
//        //目标文件夹不存在则创建
//        File targetPathDirectory = new File(syncInfo.getTargetPath());
//        if (!targetPathDirectory.exists()) {
//            targetPathDirectory.mkdirs();
//        }
//        //允许拷贝哪些后缀的文件
//        String[] fileNameSuffixs = StringUtil.isEmpty(syncInfo.getFileNameSuffix()) ? null :
//                syncInfo.getFileNameSuffix().split(",");
//        StringBuffer times = new StringBuffer().append("[").append(beginDate).append(",").append(endDate).append("]");
//        //读取并设置请求参数
//        Map<String, Object> params = new HashMap<>();
//        params.putAll(postParams);
//        params.put("serviceNodeId", "NMIC_MUSIC_CMADAAS");
//
//        if(StringUtil.isEmpty(syncInfo.getCimissResultDataType())) {
//            params.put("dataFormat", "json");
//        }else{
//            params.put("dataFormat", syncInfo.getCimissResultDataType().toLowerCase());
//        }
//        params.put("dataCode", syncInfo.getCimissDataCode());
//        params.put("interfaceId", syncInfo.getCimissInterfaceId());
//        params.put("timeRange", times.toString());
//        params.put("elements", syncInfo.getCimissElements());
//
//        if (!StringUtil.isEmpty(syncInfo.getCimissEleValueRanges())) {
//            params.put("eleValueRanges", syncInfo.getCimissEleValueRanges());
//        }
//        if (!StringUtil.isEmpty(syncInfo.getCimissOrderBy())) {
//            params.put("orderBy", syncInfo.getCimissOrderBy());
//        }
//        //AK认证
//        params.put("userId", syncInfo.getCimissUserName());
//        params.put("pwd", syncInfo.getCimissPwd());
//        params.put("timestamp", Long.valueOf(System.currentTimeMillis()).toString());
//        params.put("nonce", Double.valueOf(new Random().nextDouble()).toString());
//        params.put("sign", SdpSignUtil.getSignString(params));
//        params.remove("pwd");
//
//        String resultStr = requestDataJson(syncInfo, msgList, params);
//
//        if (!StringUtil.isEmpty(resultStr)) {
//            //输出为文件
//            FileUtil.writeString(resultStr, outFilePath, "UTF-8");
//            copyConut++;
//            StringBuffer sb = new StringBuffer();
//            sb.append("同步新文件：").append("大数据云平台 --> ").append(outFilePath);
//            msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.SUCCESS, sb.toString()));
//            DataSyncLogUtil.add(syncInfo.getId(), DataSyncLogUtil.LogType.ADD, sb.toString());
//            //同步项发现新文件时全局事件
//            Map<String, Object> eventData = new HashMap<>();
//            eventData.put("syncInfo", syncInfo);
//            eventData.put("currDate", DateUtil.getCurDateTimeStr());
//            eventData.put("filePath", outFilePath);
//            DataSyncEvent dataSyncEvent = new DataSyncEvent("DataSync.onNewFile", eventData);
//            DataSyncConfig.applicationEventPublisher.publishEvent(dataSyncEvent);
//        } else {
//            StringBuffer sb = new StringBuffer();
//            sb.append("同步新文件[").append(outFilePath).append("]失败:").append("请求异常或大数据云平台返回结果为空!");
//            log.error(sb.toString());
//            msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.ERROR, sb.toString()));
//            DataSyncLogUtil.add(syncInfo.getId(), DataSyncLogUtil.LogType.FAIL, sb.toString());
//        }
//
//        System.out.println(msg);
//        msgList.add(new EsayMsgBean(DateUtil.getCurrentDate(), EsayMsgBean.MsgType.INFO, msg.toString()));
//        return "Success";
//    }
//}
