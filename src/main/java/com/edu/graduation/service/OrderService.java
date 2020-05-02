package com.edu.graduation.service;

import com.edu.graduation.entity.dto.CreateOrderDTO;
import com.edu.graduation.vo.ResultVo;

public interface OrderService {

    ResultVo createOrder(CreateOrderDTO createOrderDTO);

    /**
     * 支付成功，修改订单状态
     * @param orderId
     * @return
     */
    ResultVo payFinish(String orderId);

    /**
     * 支付失败，修改状态
     * @param orderId
     * @return
     */
    ResultVo payFail(String orderId);

    /**
     * 微信用户获取自己的订单列表
     * @param userId
     * @return
     */
    ResultVo getSelf(String userId);
}
