package com.edu.graduation.service;

import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.DistinguishDTO;
import com.edu.graduation.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResultVo test_upload(MultipartFile file);

    /**
     * 上传图片到百度的自定义识别的图库中
     * @return
     */
    int upload2Baidu(Plate plate);

    /**
     * 删除百度图库中的图片
     * @param plate
     * @return
     */
    int deleteFromBaidu(Plate plate);

    /**
     * 小程序上传图片，识别
     * @param distinguishDTO
     * @return
     */
    ResultVo distinguishPlate(DistinguishDTO distinguishDTO);


}
