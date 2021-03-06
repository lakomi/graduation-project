package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.ModifySelfDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.StoreService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/store")
@CrossOrigin
@Slf4j
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/getAllStores")
    public ResultVo getAllStores(){
        return storeService.getAllStores();
    }

    @GetMapping("/getself")
    public ResultVo getStoreData(@RequestParam("userId") String userId){
        return storeService.getStoreData(userId);
    }


    @PostMapping("/modifyPw")
    public ResultVo modifyPassword(@RequestParam("userId") String userId,@RequestParam("newPW")String newPassword,@RequestParam("oldPW")String oldPassword){
        return storeService.modifyPassword(userId,newPassword,oldPassword);
    }


    @PostMapping("/modifySelf")
    public ResultVo modifyDataSelf(@Valid ModifySelfDTO modifySelfDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("/modifySelf @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return storeService.modifyData(modifySelfDTO);
    }







}
