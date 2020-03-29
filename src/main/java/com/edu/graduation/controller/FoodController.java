package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.FoodService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author q
 * @Date 18-7-21 下午7:31
 */
@RestController
@RequestMapping("/food")
@CrossOrigin
@Slf4j
public class FoodController {

    @Autowired
    private FoodService foodService;


    @ApiOperation(value = "获取某一家的所有菜品列表。返回信息有编号，名称，单价，盘子图片，备注")
    @GetMapping("/getAllFood")
    public ResultVo getAllFood(@RequestParam("storeId") Integer storeId){
        return foodService.getAllFoodList(storeId);
    }

    @GetMapping("/getOneFood")
    public ResultVo getOneFood(@RequestParam("foodId")String foodId){
        return foodService.getOneFood(foodId);
    }


    @ApiOperation(value = "修改菜品的信息，可修改名称，盘子图片。单价和备注随盘子图片")
    @PostMapping("/modifyFood")
    public ResultVo modifyFood(@Valid ModifyFoodDTO modifyFoodDTO,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("/modifyFood @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return foodService.modifyFood(modifyFoodDTO);
    }

    @ApiOperation(value = "添加菜")
    @PostMapping("/addFood")
    public ResultVo addFood(@Valid AddFoodDTO addFoodDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("/addFood @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return foodService.addFood(addFoodDTO);
    }

    @ApiOperation(value = "删除菜品，设为下架")
    @PostMapping("/deleteFood")
    public ResultVo deleteFood(@RequestParam("foodId")String foodId){
        return foodService.deleteFood(foodId);
    }



}
