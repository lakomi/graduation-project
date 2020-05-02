package com.edu.graduation.service.impl;

import com.edu.graduation.dao.FoodMapper;
import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.dao.SellMapper;
import com.edu.graduation.entity.bean.Food;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.FoodService;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.FoodManageVo;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    //菜品的主键长度为5
    private static int FOOD_ID_LENGTH = 5;

    private final FoodMapper foodMapper;
    private final PlateMapper plateMapper;
    private final SellMapper sellMapper;

    public FoodServiceImpl(FoodMapper foodMapper, PlateMapper plateMapper, SellMapper sellMapper) {
        this.foodMapper = foodMapper;
        this.plateMapper = plateMapper;
        this.sellMapper = sellMapper;
    }


    @Override
    public ResultVo getAllFoodList(Integer storeId) {
        List<Food> foodList = foodMapper.getAllFoodFromOne(storeId, MessageEnum.FOOD_STATUS_ON.getCode());
        List<FoodManageVo> foodManageVoList = new ArrayList<>();
        for (Food food : foodList) {
            FoodManageVo foodManageVo = getFoodVo(food);
            foodManageVoList.add(foodManageVo);
        }
        return ResultVoUtil.success(foodManageVoList);
    }


    @Override
    @Transactional
    public ResultVo modifyFood(ModifyFoodDTO modifyFoodDTO) {
        if (StringUtils.isEmpty(modifyFoodDTO.getPlateId()))
            return modifyNoPlate(modifyFoodDTO.getFoodId(), modifyFoodDTO.getFoodName());
        else
            return modifyPlate(modifyFoodDTO.getFoodId(), modifyFoodDTO.getFoodName(), modifyFoodDTO.getPlateId());
    }

    //盘子id没有变化,只有名字发生变化
    private ResultVo modifyNoPlate(String foodId, String foodName) {

        Food food = foodMapper.getOneById(foodId);
        food.setFoodName(foodName);
        //修改菜品信息
        if (foodMapper.updateFood(food) == 1) {
            return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
        } else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    //盘子id有变化
    private ResultVo modifyPlate(String foodId, String foodName, String plateId) {
        //查询去原先的菜品信息
        Food food = foodMapper.getOneById(foodId);
        //旧盘子
        String oldPlateId = food.getPlateId();
        //新盘子信息
        Plate newPlate = plateMapper.getPlateById(plateId);

        food.setFoodName(foodName);
        food.setPlateId(plateId);
        food.setFoodPrice(newPlate.getPrice());

        int flag_one = 0, flag_two = 0, flag_three = 0, flag = 0;
        //修改菜品信息
        flag_one = foodMapper.updateFood(food);
        Integer oldPlateCount = plateMapper.getUsedCountById(oldPlateId);
        //进行修改盘子被使用数量信息。旧盘子-1，新盘子+1
        if (!StringUtils.isEmpty(oldPlateId) && !StringUtils.isEmpty(oldPlateCount)) {   //菜品之前有盘子,且盘子还在使用，并没有删
            //修改旧盘子数量
            flag_two = plateMapper.modifyPlateUsedCount(oldPlateId, oldPlateCount - 1);
            //修改新盘子数量
            flag_three = plateMapper.modifyPlateUsedCount(plateId, newPlate.getUsedCount() + 1);

            if (flag_one == 1 && flag_two == 1 && flag_three == 1)
                flag = 1;
        } else {     //菜品之前没有盘子
            //直接修改新盘子即可
            flag_three = plateMapper.modifyPlateUsedCount(plateId, newPlate.getUsedCount() + 1);
            if (flag_one == 1 && flag_three == 1)
                flag = 1;
        }

        if (flag == 1)
            return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
        else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    @Override
    @Transactional
    public ResultVo addFood(AddFoodDTO addFoodDTO) {
        Food food = new Food();
        BeanUtils.copyProperties(addFoodDTO, food);
        if (StringUtils.isEmpty(addFoodDTO.getPlateId())) {
            food.setFoodId(KeyUtil.getRandomString(FOOD_ID_LENGTH));
            food.setFoodStatus(MessageEnum.FOOD_STATUS_ON.getCode());
            //添加
            int one = foodMapper.addFood(food);
            if (one == 1) {
                return ResultVoUtil.success(BackMessageEnum.ADD_SUCCESS.getMessage());
            } else
                throw new MyException(MyExceptionEnum.SQL_ERROR);
        } else {
            Double price = plateMapper.getPriceById(addFoodDTO.getPlateId());
            food.setFoodPrice(price);
            food.setFoodId(KeyUtil.getRandomString(FOOD_ID_LENGTH));
            food.setFoodStatus(MessageEnum.FOOD_STATUS_ON.getCode());
            //添加
            int one = foodMapper.addFood(food);
            //修改盘子的被引用数量
            Integer cout = plateMapper.getUsedCountById(addFoodDTO.getPlateId());
            int two = plateMapper.modifyPlateUsedCount(addFoodDTO.getPlateId(), cout + 1);
            if ((one == 1) && (two == 1)) {
                return ResultVoUtil.success(BackMessageEnum.ADD_SUCCESS.getMessage());
            } else
                throw new MyException(MyExceptionEnum.SQL_ERROR);
        }
    }

    @Override
    @Transactional
    public ResultVo deleteFood(String foodId) {
        int one = 0, two = 0, flag = 0;
        //删除菜品,仅仅将其设为下架
        one = foodMapper.modifyFoodStatus(foodId, MessageEnum.FOOD_STATUS_OFF.getCode());
        //修改盘子被引用数量,数量-1
        Food food = foodMapper.getOneById(foodId);
        Integer count = plateMapper.getUsedCountById(food.getPlateId());
        //有盘子，则盘子的数量需要修改
        if (!StringUtils.isEmpty(food.getPlateId()) && !StringUtils.isEmpty(count)) {
            two = plateMapper.modifyPlateUsedCount(food.getPlateId(), count - 1);
            if (one == 1 && two == 1)
                flag = 1;
        } else   //没有盘子，仅仅修改菜品状态即可
            if (one == 1)
                flag = 1;
        if (flag == 1) {
            return ResultVoUtil.success(BackMessageEnum.DEL_SUCCESS.getMessage());
        } else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    @Override
    public ResultVo getOneFood(String foodId) {
        Food food = foodMapper.getOneById(foodId);
        FoodManageVo foodManageVo = getFoodVo(food);
        return ResultVoUtil.success(foodManageVo);
    }

    @Override
    public ResultVo getFoodOff(Integer storeId) {
        List<Food> foodList = foodMapper.getAllFoodFromOne(storeId, MessageEnum.FOOD_STATUS_OFF.getCode());
        List<FoodManageVo> foodManageVoList = new ArrayList<>();
        for (Food food : foodList) {
            FoodManageVo foodManageVo = getFoodVo(food);
            foodManageVoList.add(foodManageVo);
        }
        return ResultVoUtil.success(foodManageVoList);
    }

    @Override
    @Transactional
    public ResultVo foodOn(String foodId) {
        int flag = 0, flag_one = 0, flag_two = 0;
        flag_one = foodMapper.modifyFoodStatus(foodId, MessageEnum.FOOD_STATUS_ON.getCode());
        Food food = foodMapper.getOneById(foodId);
        Integer plateCount = plateMapper.getUsedCountById(food.getPlateId());
        //有盘子,则对应盘子被引用数+1
        if (!StringUtils.isEmpty(food.getPlateId()) && !StringUtils.isEmpty(plateCount)) {
            flag_two = plateMapper.modifyPlateUsedCount(food.getPlateId(), plateCount + 1);
            if (flag_one == 1 && flag_two == 1)
                flag = 1;
        } else    //没有盘子。仅仅修改菜品状态即可
            if (flag_one == 1)
                flag = 1;

        if (flag == 1)
            return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
        else
            throw new MyException(MyExceptionEnum.SQL_ERROR);
    }

    //整理成菜品vo的数据信息
    private FoodManageVo getFoodVo(Food food) {
        FoodManageVo foodManageVo = new FoodManageVo();
        foodManageVo.setFoodId(food.getFoodId());
        foodManageVo.setFoodName(food.getFoodName());
        Plate plate = plateMapper.getPlateById(food.getPlateId());
        if (!StringUtils.isEmpty(plate)) {
            foodManageVo.setPlatePhoto(plate.getPicture());
            foodManageVo.setRemark(plate.getRemark());
            foodManageVo.setFoodPrice(plate.getPrice());
        }
        return foodManageVo;
    }
}
