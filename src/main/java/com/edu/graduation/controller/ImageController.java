package com.edu.graduation.controller;

import com.edu.graduation.AI.Same;
import com.edu.graduation.AI.Similar;
import com.edu.graduation.service.ImageService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/file")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("test-upload")
    public ResultVo test_upload(MultipartFile file) {
        return imageService.test_upload(file);
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

        String s = Same.sameHqAdd(file);
        ResultVo resultVo = ResultVoUtil.success(s);
        return resultVo;
    }

    @PostMapping("/search-sa")
    public ResultVo searchImageSame(MultipartFile file) {
//        String s = ImageToBase64Util.getImageStrFromPath(file);

        String s = Same.sameHqSearch(file);
        ResultVo resultVo = ResultVoUtil.success(s);
        return resultVo;
    }
}
