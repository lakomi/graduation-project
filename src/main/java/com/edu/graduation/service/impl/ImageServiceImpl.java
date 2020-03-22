package com.edu.graduation.service.impl;

import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.service.ImageService;
import com.edu.graduation.utils.ImageToBase64Util;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageServiceImpl implements ImageService {


    @Autowired
    private PlateMapper plateMapper;

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


}
