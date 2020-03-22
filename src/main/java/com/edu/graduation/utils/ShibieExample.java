package com.edu.graduation.utils;


import com.edu.graduation.AI.BaiduImageSearch;
import com.edu.graduation.AI.util.Base64Util;
import com.edu.graduation.AI.util.FileUtil;
import com.edu.graduation.AI.util.HttpUtil;

import java.net.URLEncoder;

public class ShibieExample {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String dishAdd() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/dish/add";
        try {
            // 本地文件路径
            String filePath = "C:\\Users\\AHRL\\Desktop\\本地图库\\8.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "brief=" + "{\"name\":\"6.5\", \"id\":\"2020lafd2\"}" + "&image=" + imgParam + "&sub_lib=" + "18453210";

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "[调用鉴权接口获取的token]";
            String accessToken = BaiduImageSearch.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dishSearch() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/dish/search";
        try {
            // 本地文件路径
            String filePath = "C:\\Users\\AHRL\\Desktop\\本地图库\\10.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam + "&sub_lib=" + "18453210";

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = BaiduImageSearch.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ShibieExample.dishSearch();
//        ShibieExample.dishAdd();
    }




}
