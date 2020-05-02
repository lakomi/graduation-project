package com.edu.graduation.controller;

import com.edu.graduation.AI.Same;
import com.edu.graduation.AI.Similar;
import com.edu.graduation.entity.dto.DistinguishDTO;
import com.edu.graduation.service.ImageService;
import com.edu.graduation.utils.ImageToBase64Util;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/file")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/test-upload")
    public ResultVo test_upload(MultipartFile fileData) throws UnsupportedEncodingException {
//        try {
//            byte[] imgData = ImageToBase64Util.imageToByte(fileData);
//            String imgStr = Base64Util.encode(imgData);
            String img = ImageToBase64Util.getImageStrFromPath(fileData);
            String str = URLEncoder.encode(img, "UTF-8");

            List<String > strings = new ArrayList<>();
            strings.add(str);
            DistinguishDTO distinguishDTO = new DistinguishDTO();
            distinguishDTO.setStoreId(2);
            distinguishDTO.setPlateCodeList(strings);
            return imageService.distinguishPlate(distinguishDTO);

//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    /**
     * 小程序上传图片识别
     * @param distinguishDTO
     * @return
     */
    @PostMapping("/upload_photo")
    public ResultVo test_upload1(@RequestBody DistinguishDTO distinguishDTO) {
        return imageService.distinguishPlate(distinguishDTO);
    }


    @PostMapping("/upload-si")
    public ResultVo upLoadImageSimilar(MultipartFile file) {
//        String s = ImageToBase64Util.getImageStrFromPath(file);

        String s = Similar.similarAdd(file);
        ResultVo resultVo = ResultVoUtil.success(s);
        return resultVo;
    }

    @PostMapping("/search-si")
    public ResultVo searchImageSimilar(MultipartFile file) {
//        String s = ImageToBase64Util.getImageStrFromPath(file);

        String s = Similar.similarSearch(file);
        ResultVo resultVo = ResultVoUtil.success(s);
        return resultVo;
    }

    @PostMapping("/upload-sa")
    public ResultVo upLoadImageSame(MultipartFile file) {
//        String s = ImageToBase64Util.getImageStrFromPath(file);

//        String s = Same.sameHqAdd(file);
//        ResultVo resultVo = ResultVoUtil.success(s);
        return null;
    }

    @PostMapping("/search-sa")
    public ResultVo searchImageSame(MultipartFile file) {
//        String s = ImageToBase64Util.getImageStrFromPath(file);

        String s = Same.sameHqSearch(file);
        ResultVo resultVo = ResultVoUtil.success(s);
        return resultVo;
    }
}
