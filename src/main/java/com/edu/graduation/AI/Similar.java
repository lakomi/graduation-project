package com.edu.graduation.AI;


import com.edu.graduation.AI.util.Base64Util;
import com.edu.graduation.AI.util.HttpUtil;
import com.edu.graduation.utils.ImageToBase64Util;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;

public class Similar {

    public static String similarAdd(MultipartFile file) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/similar/add";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = ImageToBase64Util.imageToByte(file);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "brief=" + "{\"name\":\"盘子\", \"id\":\"2020lafd\"}" + "&image=" + imgParam + "&tags=" + "1,1";

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

    public static String similarSearch(MultipartFile file) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/similar/search";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            byte[] imgData = ImageToBase64Util.imageToByte(file);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam + "&pn=" + 200 + "&rn=" + 100;

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


}
