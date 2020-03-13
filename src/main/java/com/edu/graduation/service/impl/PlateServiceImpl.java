package com.edu.graduation.service.impl;

import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.service.PlateService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.PlateVo;
import com.edu.graduation.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {

    @Autowired
    private PlateMapper plateMapper;


    @Override
    public ResultVo getAllPlates(Integer storeId) {
        List<Plate> plateList = plateMapper.getPlateListByStoreId(storeId);

        List<PlateVo> plateVoList = new ArrayList<>();
        for (Plate plate:plateList) {
            PlateVo plateVo = new PlateVo();
            BeanUtils.copyProperties(plate,plateVo);
            plateVoList.add(plateVo);
        }
        return ResultVoUtil.success(plateVoList);
    }






}
