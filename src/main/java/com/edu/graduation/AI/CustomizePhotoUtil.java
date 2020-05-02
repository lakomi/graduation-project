package com.edu.graduation.AI;

import com.edu.graduation.AI.util.Base64Util;
import com.edu.graduation.AI.util.FileUtil;
import com.edu.graduation.AI.util.HttpUtil;
import com.edu.graduation.enums.MessageEnum;
import com.edu.graduation.service.RedisService;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;

@Component
public class CustomizePhotoUtil {

    private final RedisService redisService;

    public CustomizePhotoUtil(RedisService redisService) {
        this.redisService = redisService;
    }

    public String myPhotoUtil(String url,String param) {
        try {
            // 获取access_token
            String accessToken = redisService.getAuthValue();
            //请求得到结果
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String myPhotoSearch() {
        try {
            // 本地文件路径
            String filePath = "C:\\Users\\AHRL\\Desktop\\本地图库\\10.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam + "&sub_lib=" + MessageEnum.APP_ID.getMessage();

            // 获取access_token
            String accessToken = redisService.getAuthValue();

            String result = HttpUtil.post(MessageEnum.SEARCH_CUSTOMIZE_URL.getMessage(), accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String myPhotoDelete() {
        try {
            // 本地文件路径
            String filePath = "C:\\Users\\AHRL\\Desktop\\本地图库\\10.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam + "&sub_lib=" + MessageEnum.APP_ID.getMessage();

            // 获取access_token
            String accessToken =BaiduImageSearch.getAuth();

            String result = HttpUtil.post(MessageEnum.DELETE_CUSTOMIZE_URL.getMessage(), accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        CustomizePhotoUtil.myPhotoDelete();

    }

}
