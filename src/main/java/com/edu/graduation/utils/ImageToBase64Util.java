package com.edu.graduation.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片与base64编码之间的转换
 */
public class ImageToBase64Util {

//    File file1 = new File("E:\\1.jpg");

    /**
     * 本地图片转化为base64编码字符串
     *
     * @return
     */
    public static String getImageStrFromPath(MultipartFile file) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            /** FileInputStream就实现了available方法，那么就可以用new byte[in.available()];*/
            inputStream = file.getInputStream();
            /** 得到数据流里有多少个字节可以读取
             *  在网络中获取时，会遇到问题，比如 对方发送了1000字节，但调用available（）却得到的不是1000,
             *  因为数据还没有完全送达本地，分批次到达。需要调用多次available（）
             * */
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /** 对字节数组Base64编码*/
        BASE64Encoder encoder = new BASE64Encoder();
        String imageString = encoder.encode(data);

        return imageString;
    }

    /**
     * 本地图片转化为base64编码字符串
     *
     * @return
     */
    public static byte[] imageToByte (MultipartFile file) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = file.getInputStream();
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * base64字符串转化为图片
     *
     * @param imgStr
     * @return
     */
    public static boolean GenerateImage(String imgStr) {
        // 图片为空
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; i++) {
                // 调整异常数据
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            String imgFilePath = "/home/ahri/Desktop/qq/test22.bmp";
            OutputStream outputStream = new FileOutputStream(imgFilePath);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 网络图片转化为base64字符串
     *
     * @param imageUrl
     * @return
     */
    public static String getImageStrFromUrl(String imageUrl) {

        byte[] data = null;
        try {
            // 创建URL
            URL url = new URL(imageUrl);

            //创建链接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10 * 1000);

            InputStream inputStream = connection.getInputStream();
            data = readInputStream(inputStream);


        } catch (Exception e) {
            e.printStackTrace();
        }

        /** 对字节数组Base64编码*/
        BASE64Encoder encoder = new BASE64Encoder();
        String imageString = encoder.encode(data);

        return imageString;
    }

    /**
     * 读取网络图片时，若图片过大，采用读取本地时的方法会读不全，因此可采用一下方法。
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;

        while ((len = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }

        inputStream.close();
        return outputStream.toByteArray();
    }

    public static void main(String[] args) {

    }






}
