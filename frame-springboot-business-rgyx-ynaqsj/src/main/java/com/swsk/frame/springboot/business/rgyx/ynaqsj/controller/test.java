package com.swsk.frame.springboot.business.rgyx.ynaqsj.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangyongqi
 * @date 2023/8/28
 */

public class test {
    private static final Map<String, String> fy2Types = new HashMap<>();

    static {
        // CTH=云顶高度，CTT=云顶温度，HSC=过冷层厚度、COT=光学厚度、TBB=黑体亮温
        // json文件外面的key是小写
        // 云顶温度
        fy2Types.put("WMC-K.0001.0000.T", "ctt");
        // 云顶高度
        fy2Types.put("WMC-K.0001.0001.T", "cth");
        // 过冷层厚度
        fy2Types.put("WMC-K.0001.0002.T", "hsc");
        // 光学厚度
        fy2Types.put("WMC-K.0001.0003.T", "cot");
        // 黑体亮温
        fy2Types.put("WMC-K.0001.0004.T", "tbb");
    }

    public static List<String> readFile(File filePath, String charsetName) throws IOException {
        List<String> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charsetName);
             BufferedReader br = new BufferedReader(inputStreamReader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        List<String> list = readFile(new File("C:\\Users\\ASUS\\Desktop\\test\\rainParseInfo"), "utf-8");


        String radarNoParse = list.get(0).replace(" ", "");
        radarNoParse = radarNoParse.substring(radarNoParse.indexOf("=") + 1);
        String filenameParse = list.get(1).replace(" ", "");
        filenameParse = filenameParse.substring(filenameParse.indexOf("=") + 1);
        String lastMonomerJsonParse = list.get(2).replace(" ", "");
        lastMonomerJsonParse = lastMonomerJsonParse.substring(lastMonomerJsonParse.indexOf("=") + 1);
        Map<String, String> map = new HashMap<>();
        map.put("radarNo", radarNoParse);
        map.put("radarFile", filenameParse);
        map.put("lastMonomerJson", lastMonomerJsonParse);


        System.out.println(map);
    }


}
