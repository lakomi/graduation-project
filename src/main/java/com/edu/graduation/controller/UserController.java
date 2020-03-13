package com.edu.graduation.controller;


import com.edu.graduation.entity.dto.LoginDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.UserService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author q
 * @Date 18-7-21 下午6:28
 */
@RestController
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 管理员登录
     *
     * @param loginDTO
     * @param bindingResult
     * @param response
     * @return
     */
    @ApiOperation(value = "商家登录")
    @PostMapping("/loginA")
    public ResultVo AdminLogin(@Valid LoginDTO loginDTO, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("/login @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.loginR(loginDTO, response);
    }

    /**
     * 用户登录
     *
     * @param loginDTO
     * @param bindingResult
     * @param response
     * @return
     */
    @ApiOperation(value = "目前暂定用户登录")
    @PostMapping("/loginU")
    public ResultVo UserLogin(@Valid LoginDTO loginDTO, BindingResult bindingResult, HttpServletResponse response) {

       if (bindingResult.hasErrors()) {
            log.info("/login @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.loginT(loginDTO, response);
    }



}
