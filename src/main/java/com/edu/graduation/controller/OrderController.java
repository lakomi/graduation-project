package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.CreateOrderDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.OrderService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResultVo createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("/order/create @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return orderService.createOrder(createOrderDTO);
    }

    @GetMapping("/payFinish")
    public ResultVo payFinish(String orderId) {
        log.info("/order/payFinish");
        return orderService.payFinish(orderId);
    }

    @GetMapping("/payFail")
    public ResultVo payFail(String orderId) {
        log.info("/order/payFail");
        return orderService.payFail(orderId);
    }

    @GetMapping("/getSelf")
    public ResultVo getSelf(String userId){
        log.info("/order/getSelf");
        return orderService.getSelf(userId);
    }


}
