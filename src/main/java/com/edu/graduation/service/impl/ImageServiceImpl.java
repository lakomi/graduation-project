package com.edu.graduation.service.impl;

import com.edu.graduation.AI.CustomizePhotoUtil;
import com.edu.graduation.AI.util.GsonUtils;
import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.entity.*;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.DistinguishDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.ImageService;
import com.edu.graduation.utils.ImageToBase64Util;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class ImageServiceImpl implements ImageService {

    private final CustomizePhotoUtil customizePhotoUtil;
    private final PlateMapper plateMapper;

    public ImageServiceImpl(CustomizePhotoUtil customizePhotoUtil, PlateMapper plateMapper) {
        this.customizePhotoUtil = customizePhotoUtil;
        this.plateMapper = plateMapper;
    }

    @Override
    public ResultVo test_upload(MultipartFile file) {

        Plate plate = new Plate();
        plate.setPlateId(KeyUtil.getRandomString(4));
        plate.setPrice(12.5);
        plate.setUsedCount(0);
        plate.setRemark("荤");
        plate.setStoreId(111);
        String imageCode = ImageToBase64Util.getImageStrFromPath(file);
        plate.setPicture(imageCode);
        int flag = plateMapper.insertPlate(plate);
        if (flag == 1) {
            return ResultVoUtil.success(BackMessageEnum.ADD_SUCCESS.getMessage());
        } else {
            return ResultVoUtil.success(MyExceptionEnum.SQL_ERROR.getMessage());
        }
    }

    @Override
    public int upload2Baidu(Plate plate) {
        try {
            //对图片的base64编码进行处理
            String imgParam = URLEncoder.encode(plate.getPicture(), "UTF-8");
            //摘要，存盘子的id及其对应价格
            Brief brief = new Brief(plate.getPrice(), plate.getPlateId());
            //请求应携带的内容
            String param = "brief=" + brief + "&image=" + imgParam + "&sub_lib=" + MessageEnum.APP_ID.getMessage();

            String add_result = customizePhotoUtil.myPhotoUtil(MessageEnum.ADD_CUSTOMIZE_URL.getMessage(), param);
            //处理返回的数据，转成相应的bean
            CustomizeResult addCustomizeResult = GsonUtils.fromJson(add_result, CustomizeResult.class);
            //没有错误码。即成功
            if (StringUtils.isEmpty(addCustomizeResult.getError_code()) || addCustomizeResult.getError_code() == null) {
                return 1;
            } else {
                throw new MyException(MyExceptionEnum.ADD_THIRD_ERROR);
            }
        } catch (UnsupportedEncodingException e) {
            throw new MyException(MyExceptionEnum.ADD_THIRD_ERROR);
        }
    }

    @Override
    public int deleteFromBaidu(Plate plate) {
        try {
            //对图片的base64编码进行处理
            String imgParam = URLEncoder.encode(plate.getPicture(), "UTF-8");
            //请求应携带的内容
            String param = "image=" + imgParam + "&sub_lib=" + MessageEnum.APP_ID.getMessage();
            //删除百度图库中的
            String add_result = customizePhotoUtil.myPhotoUtil(MessageEnum.DELETE_CUSTOMIZE_URL.getMessage(), param);
            //处理返回的数据，转成相应的bean
            CustomizeResult addCustomizeResult = GsonUtils.fromJson(add_result, CustomizeResult.class);
            //没有错误码。即成功
            if (StringUtils.isEmpty(addCustomizeResult.getError_code()) || addCustomizeResult.getError_code() == null) {
                return 1;
            } else {
                throw new MyException(MyExceptionEnum.DELETE_THIRD_ERROR);
            }
        } catch (UnsupportedEncodingException e) {
            throw new MyException(MyExceptionEnum.DELETE_THIRD_ERROR);
        }
    }

    @Override
    public ResultVo distinguishPlate(DistinguishDTO distinguishDTO) {
        List<List<Map<String,String>>> lists = new ArrayList<>();

        for (String string : distinguishDTO.getPlateCodeList()) {
            List<Map<String,String>> mapList = new ArrayList<>();
            //一张图上有好几个图片结果
            List<Brief> briefList = getResultFromBaidu(string);
            for (Brief brief:briefList) {
                Map<String, String> map = new TreeMap<>();
                map.put("price", "");
                map.put("remark", "");
                map.put("plateId", "");

                //仅当识别结果不为空时，才判断
                if (!StringUtils.isEmpty(brief.getId())) {
                    Plate plate = plateMapper.getPlateById(brief.getId());
                    //识别到的盘子，是用户当前所在的店铺的
                    if (plate.getStoreId().equals(distinguishDTO.getStoreId())) {
                        map.put("price", plate.getPrice().toString());
                        map.put("remark", plate.getRemark());
                        map.put("plateId", plate.getPlateId());
                    }
                }
                mapList.add(map);
            }
            lists.add(mapList);
        }
        return ResultVoUtil.success(lists);
    }

    /**
     * 自定义图片识别的搜索
     *
     * @param photoCode
     * @return
     */
    private List<Brief> getResultFromBaidu(String photoCode) {
        //一张图有多个盘子的情况
        List<Brief> briefList = new ArrayList<>();
        try {
            //对图片的base64编码进行处理
            String imgParam = URLEncoder.encode(photoCode, "UTF-8");
            //请求应携带的内容
            String param = "image=" + imgParam + "&sub_lib=" + MessageEnum.APP_ID.getMessage();
            //删除百度图库中的
            String add_result = customizePhotoUtil.myPhotoUtil(MessageEnum.SEARCH_CUSTOMIZE_URL.getMessage(), param);
            //处理返回的数据，转成相应的bean
            DistinguishResult distinguishResult = GsonUtils.fromJson(add_result, DistinguishResult.class);
            //没有错误
            if (StringUtils.isEmpty(distinguishResult.getError_code()) || distinguishResult.getError_code() == null) {
                if (distinguishResult.getResult_num() > 0) {
                    //遍历result。即图中有几个盘子
                    for (LocationList locationList: distinguishResult.getResult()) {
                        List<Dish> dishList = locationList.getDishes();
                        //上传时，图片对应的id和价格
                        Brief brief = new Brief();
                        //找到第一个，若其score够大，近似认为识别到
                        if (dishList.get(0).getScore() >= 0.9) {
                            brief = GsonUtils.fromJson(dishList.get(0).getBrief(), Brief.class);
                        }
                        briefList.add(brief);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
        }
        return briefList;
    }


}
