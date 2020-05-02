package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.FoodService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName AdminController
 * @Description
 * @Author q
 * @Date 18-7-21 下午7:31
 */
@RestController
@RequestMapping("/food")
@CrossOrigin
@Slf4j
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }



    @GetMapping("/getAllFood")
    public ResultVo getAllFood(@RequestParam("storeId") Integer storeId){
        return foodService.getAllFoodList(storeId);
    }

    @GetMapping("/getOneFood")
    public ResultVo getOneFood(@RequestParam("foodId")String foodId){
        return foodService.getOneFood(foodId);
    }

    @GetMapping("/getFoodOff")
    public ResultVo getFoodOff(@RequestParam("storeId") Integer storeId){
        return foodService.getFoodOff(storeId);
    }

    @PostMapping("/food_on")
    public ResultVo foodOn(@RequestParam("foodId")String foodId){
        return foodService.foodOn(foodId);
    }

    @PostMapping("/modifyFood")
    public ResultVo modifyFood(@Valid ModifyFoodDTO modifyFoodDTO,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("/modifyFood @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return foodService.modifyFood(modifyFoodDTO);
    }

    @PostMapping("/addFood")
    public ResultVo addFood(@Valid AddFoodDTO addFoodDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("/addFood @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return foodService.addFood(addFoodDTO);
    }

    @PostMapping("/deleteFood")
    public ResultVo deleteFood(@RequestParam("foodId")String foodId){
        return foodService.deleteFood(foodId);
    }



}
