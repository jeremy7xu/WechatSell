package com.jeremy7.sell.service.impl;

import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.service.BuyerService;
import com.jeremy7.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

      OrderDTO  orderDTO =  checkOrderOwner(openid,orderId);

        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {

        OrderDTO  orderDTO =  checkOrderOwner(openid,orderId);

        if(orderDTO == null){

            log.error("【取消订单】 查不到该订单，orderId={}", orderId);

            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);

        }


        return orderService.cancel(orderDTO);
    }

    private  OrderDTO checkOrderOwner(String openid, String orderId){

        OrderDTO orderDTO = orderService.findone(openid);

        if(orderDTO == null){

            return  null;

        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){

            log.error("【查询订单】 订单的openid不一致.openid=[], orderDTO={}" ,openid, orderDTO);

            throw  new SellException(ResultEnum.ORDER_OWNWE_ERROR);

        }

        return  orderDTO;




    }
}
