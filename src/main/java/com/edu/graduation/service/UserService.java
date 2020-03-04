package com.edu.graduation.service;


import com.edu.graduation.entity.dto.LoginDTO;
import com.edu.graduation.vo.ResultVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author q
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginDTO
     * @param response
     * @return
     */
    ResultVo loginT(LoginDTO loginDTO, HttpServletResponse response);

    /**
     * 管理员登录
     * @param loginDTO
     * @param response
     * @return
     */
    ResultVo loginR(LoginDTO loginDTO, HttpServletResponse response);


}
