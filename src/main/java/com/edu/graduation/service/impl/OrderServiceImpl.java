package com.edu.graduation.service.impl;

import com.edu.graduation.dao.OrderMapper;
import com.edu.graduation.dao.OrderdetailMapper;
import com.edu.graduation.dao.PlateMapper;
import com.edu.graduation.dao.StoreMapper;
import com.edu.graduation.entity.bean.Order;
import com.edu.graduation.entity.bean.OrderDetail;
import com.edu.graduation.entity.bean.Plate;
import com.edu.graduation.entity.dto.CreateOrderDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.OrderService;
import com.edu.graduation.utils.DateUtil;
import com.edu.graduation.utils.KeyUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.OrderListVo;
import com.edu.graduation.vo.OrderVo;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static Integer ORDER_DETAIL_COUNT = 1;

    // 截取时间
    private static Integer SPLIT_TIME_INDEX = 10;

    //已支付
    private static Integer ORDER_STATUS_FINISH = 1;
    private static String ORDER_STATUS_FINISH_COLOR = "green";
    //待支付
    private static Integer  ORDER_STATUS_WAITPAY = 0;
    //支付失败
    private static Integer ORDER_STATUS_FAIL = -1;
    private static String ORDER_STATUS_FAIL_COLOR = "red";

    private final PlateMapper plateMapper;
    private final OrderdetailMapper orderdetailMapper;
    private final OrderMapper orderMapper;
    private final StoreMapper storeMapper;

    public OrderServiceImpl(PlateMapper plateMapper, OrderdetailMapper orderdetailMapper, OrderMapper orderMapper, StoreMapper storeMapper) {
        this.plateMapper = plateMapper;
        this.orderdetailMapper = orderdetailMapper;
        this.orderMapper = orderMapper;
        this.storeMapper = storeMapper;
    }

    @Override
    @Transactional
    public ResultVo createOrder(CreateOrderDTO createOrderDTO) {
        Map<String, String> map = new TreeMap<>();
        Integer storeId = createOrderDTO.getStoreId();
        String userId = createOrderDTO.getUserId();
        String day = DateUtil.getTimeDate();
        Double sum = 0.0;

        // orderId 时间+随机数
        String orderId = KeyUtil.getUniqueKey();
        // 遍历传来的盘子列表,记录到orderDetail表中
        for (String s : createOrderDTO.getPlateIdList()) {
            Plate plate = plateMapper.getPlateById(s);
            OrderDetail orderDetail = new OrderDetail(KeyUtil.getRandomString(MessageEnum.ORDER_DETAIL_ID.getCode()), storeId, orderId, s, userId, plate.getPrice(), ORDER_DETAIL_COUNT, day);

            orderdetailMapper.addOrderDetail(orderDetail);
            sum += plate.getPrice() * ORDER_DETAIL_COUNT;
        }
        // order表中记录
        Order order = new Order(orderId, storeId, userId, sum, day, ORDER_STATUS_WAITPAY);
        orderMapper.insertOrder(order);

        // 返回订单id和消费合计
        map.put("orderId", orderId);
        map.put("total", sum.toString());

        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo payFinish(String orderId) {
        int raw = orderMapper.updateState(orderId,ORDER_STATUS_FINISH);
        if (raw != 1) {
            throw new MyException(MyExceptionEnum.SQL_ERROR);
        }
        return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
    }

    @Override
    public ResultVo payFail(String orderId) {
        int raw = orderMapper.updateState(orderId,ORDER_STATUS_FAIL);
        if (raw != 1) {
            throw new MyException(MyExceptionEnum.SQL_ERROR);
        }
        return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
    }

    @Override
    public ResultVo getSelf(String userId) {
        List<Order> orderList = orderMapper.getListByUserid(userId);
        List<OrderListVo> orderListVoList = new ArrayList<>();
        log.info(orderList.size()+"");
        if (orderList.size() == 0)
            return ResultVoUtil.success(orderListVoList);
        //设为第一个
        Integer storeId = orderList.get(0).getStoreId();
        OrderListVo orderListVo = new OrderListVo();
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order:orderList) {
            orderListVo.setStoreName(storeMapper.getStoreNameById(storeId));
            // 同一个店铺
            if (storeId.equals(order.getStoreId())) {
                //status用于小程序端控制颜色
                String status = order.getStatus().equals(ORDER_STATUS_FINISH)?ORDER_STATUS_FINISH_COLOR:ORDER_STATUS_FAIL_COLOR;
                OrderVo orderVo = new OrderVo(order.getOrderId(),order.getTotal(),order.getDateDay().substring(0,SPLIT_TIME_INDEX),status);
                orderVoList.add(orderVo);
            }else{     //下一个店铺
                orderListVo.setOrders(orderVoList);
                orderListVoList.add(orderListVo);
                //重置
                orderListVo = new OrderListVo();
                orderVoList = new ArrayList<>();

                storeId = order.getStoreId();
                orderListVo.setStoreName(storeMapper.getStoreNameById(storeId));
                //用于小程序控制颜色
                String status = order.getStatus().equals(ORDER_STATUS_FINISH)?ORDER_STATUS_FINISH_COLOR:ORDER_STATUS_FAIL_COLOR;
                OrderVo orderVo = new OrderVo(order.getOrderId(),order.getTotal(),order.getDateDay().substring(0,SPLIT_TIME_INDEX),status);
                orderVoList.add(orderVo);
            }
        }
        orderListVo.setOrders(orderVoList);
        orderListVoList.add(orderListVo);
        return ResultVoUtil.success(orderListVoList);
    }


}
