package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.FoodService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
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


    @PostMapping("/getAllFood")
    public ResultVo getAllFood(@RequestParam("storeId") Integer storeId){
        return foodService.getAllFoodList(storeId);
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

        return null;

    }


}
