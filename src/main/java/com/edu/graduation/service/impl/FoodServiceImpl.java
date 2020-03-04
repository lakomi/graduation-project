package com.edu.graduation.service.impl;

import com.edu.graduation.dao.FoodMapper;
import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.dao.SellMapper;
import com.edu.graduation.entity.bean.Food;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.FoodService;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.FoodManageVo;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    //菜品的主键长度为5
    private static int FOOD_ID_LENGTH = 5;


    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private PlateMapper plateMapper;
    @Autowired
    private SellMapper sellMapper;


    @Override
    public ResultVo getAllFoodList(Integer storeId) {
        List<FoodManageVo> foodManageVoList = foodMapper.getAllFood(storeId);
        return ResultVoUtil.success(foodManageVoList);
    }


    @Override
    @Transactional
    public ResultVo modifyFood(ModifyFoodDTO modifyFoodDTO) {
        Food food = foodMapper.getOneById(modifyFoodDTO.getFoodId());
        String oldPlateId = food.getPlateId();

        Plate newPlate = plateMapper.getPlateById(modifyFoodDTO.getPlateId());
        BeanUtils.copyProperties(modifyFoodDTO,food);
        food.setFoodPrice(newPlate.getPrice());
        ResultVo resultVo = new ResultVo();

        //修改菜品信息
        if(foodMapper.updateFood(food) == 1)
            //当修改了盘子信息时，进行修改盘子被使用数量信息。旧盘子-1，新盘子+1
            if (!oldPlateId.equals(modifyFoodDTO.getPlateId())){
                Integer oldPlateCount = plateMapper.getUsedCountById(oldPlateId);
                if((plateMapper.modifyPlateUsedCount(oldPlateId,oldPlateCount-1) == 1)&&
                        (plateMapper.modifyPlateUsedCount(modifyFoodDTO.getPlateId(),newPlate.getUsedCount()+1))==1)
                    return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS);
                else
                    throw new MyException(MyExceptionEnum.SQL_ERROR);
            }else
                return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS);
        else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    @Override
    @Transactional
    public ResultVo addFood(AddFoodDTO addFoodDTO) {
        Food food = new Food();
        BeanUtils.copyProperties(addFoodDTO,food);
        Double price = plateMapper.getPriceById(addFoodDTO.getPlateId());
        food.setFoodPrice(price);
        food.setFoodId(KeyUtil.getRandomString(FOOD_ID_LENGTH));
        //添加
        int one = foodMapper.addFood(food);
        //修改盘子的被引用数量
        Integer cout = plateMapper.getUsedCountById(addFoodDTO.getPlateId());
        int two = plateMapper.modifyPlateUsedCount(addFoodDTO.getPlateId(),cout+1);
        if ((one==1)&&(two==1)){
                return ResultVoUtil.success(BackMessageEnum.ADD_SUCCESS);
        }else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    @Override
    @Transactional
    public ResultVo deleteFood(String foodId) {
        //修改盘子被引用数量,数量-1
        Food food = foodMapper.getOneById(foodId);
        Integer count = plateMapper.getUsedCountById(food.getPlateId());
        int one = plateMapper.modifyPlateUsedCount(food.getPlateId(),count-1);
        //删除菜品
        int two = foodMapper.deleteFood(foodId);

        //todo 菜品销售，订单，订单详情中分别删除
        //菜品销售中删除
        sellMapper.deleteByFoodId(foodId);





        if((one==1)&&(two==1)) {
                return ResultVoUtil.success(BackMessageEnum.DEL_SUCCESS);
        }else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }






}
