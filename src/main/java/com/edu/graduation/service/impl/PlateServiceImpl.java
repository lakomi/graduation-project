package com.edu.graduation.service.impl;

import com.edu.graduation.dao.FoodMapper;
import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.AddPlateDTO;
import com.edu.graduation.entity.dto.ModifyPlateDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.ImageService;
import com.edu.graduation.service.PlateService;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.PlateVo;
import com.edu.graduation.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlateServiceImpl implements PlateService {

    //菜品的主键长度为5
    private static int PLATE_ID_LENGTH = 4;
    //盘子初始的被使用次数
    private static int INIT_USED_COUNT = 0;


    private final PlateMapper plateMapper;
    private final FoodMapper foodMapper;
    private final ImageService imageService;

    public PlateServiceImpl(PlateMapper plateMapper, FoodMapper foodMapper, ImageService imageService) {
        this.plateMapper = plateMapper;
        this.foodMapper = foodMapper;
        this.imageService = imageService;
    }


    @Override
    public ResultVo getAllPlates(Integer storeId) {
        List<Plate> plateList = plateMapper.getPlateListByStoreId(storeId);

        List<PlateVo> plateVoList = new ArrayList<>();
        for (Plate plate : plateList) {
            PlateVo plateVo = new PlateVo();
            BeanUtils.copyProperties(plate, plateVo);
            plateVoList.add(plateVo);
        }
        return ResultVoUtil.success(plateVoList);
    }

    @Override
    public ResultVo deletePlate(String plateId) {
        Plate plate = plateMapper.getPlateById(plateId);
        //删除盘子，直接删除
        int flag = plateMapper.deletePlate(plateId);

        if (flag == 1) {
            //本地数据库存储成功后，上传到百度的云图库
            int res = imageService.deleteFromBaidu(plate);
            if (res == 1){
                return ResultVoUtil.success(BackMessageEnum.DEL_SUCCESS.getMessage());
            }else{
                throw new MyException(MyExceptionEnum.DELETE_THIRD_ERROR);
            }
        } else {
            return ResultVoUtil.success(MyExceptionEnum.SQL_ERROR.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultVo modifyPlate(ModifyPlateDTO modifyPlateDTO) {
        int flagOne = plateMapper.modifyPlate(modifyPlateDTO.getPlateId(), modifyPlateDTO.getPrice(), modifyPlateDTO.getRemark());
        int flagTwo = foodMapper.modifyFoodPrice(modifyPlateDTO.getPlateId(), modifyPlateDTO.getPrice());
        if (flagOne == 1) {
            return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
        } else {
            throw new MyException(MyExceptionEnum.SQL_ERROR);
        }
    }


    @Override
    public ResultVo addPlate(AddPlateDTO addPlateDTO) {
        Plate plate = new Plate();
        plate.setPlateId(KeyUtil.getRandomString(PLATE_ID_LENGTH));
        plate.setUsedCount(INIT_USED_COUNT);
        BeanUtils.copyProperties(addPlateDTO, plate);

        int flag = plateMapper.insertPlate(plate);
        if (flag == 1) {
            //本地数据库存储成功后，上传到百度的云图库
            int res = imageService.upload2Baidu(plate);
            if (res == 1){
                return ResultVoUtil.success(BackMessageEnum.ADD_SUCCESS.getMessage());
            }else{
                throw new MyException(MyExceptionEnum.ADD_THIRD_ERROR);
            }
        } else {
            throw new MyException(MyExceptionEnum.SQL_ERROR);
        }
    }


}
