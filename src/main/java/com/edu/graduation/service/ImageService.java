package com.edu.graduation.service;

import com.edu.graduation.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResultVo test_upload(MultipartFile file);

}
