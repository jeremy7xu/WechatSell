package com.jeremy7.sell.service.impl;

import com.jeremy7.sell.dataobject.OrderDetail;
import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.OrderStatusEnum;
import com.jeremy7.sell.enums.PayStatusEnum;
import com.jeremy7.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private  OrderServiceImpl orderService;

    private  final  String buyeropenid = "adad";

    private final  String orderid="1530185455651598348";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("jeremy's home");
        orderDTO.setBuyerName("jeremy");
        orderDTO.setBuyerPhone("12345678913");
        orderDTO.setBuyerOpenid(buyeropenid);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setProductId("7");
        orderDetail.setProductQuantity(2);
        orderDetailList.add(orderDetail);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);

        log.info("【创建订单】result={}",result);

    }

    @Test
    public void findone() {

       OrderDTO orderDTO =  orderService.findone(orderid);
       log.info("【查询单个订单】 result={}", orderDTO);
        Assert.assertEquals(orderid,orderDTO.getOrderId());

    }

    @Test
    public void findList() {

        PageRequest request = PageRequest.of(0,2);

       Page<OrderDTO> orderDTOList =  orderService.findList(buyeropenid,request);

        Assert.assertNotEquals(0,orderDTOList.getTotalElements());
    }

    @Test
    public void cancel() {

        OrderDTO orderDTO =  orderService.findone(orderid);
       OrderDTO result =  orderService.cancel(orderDTO);

       Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    public void finish() {

        OrderDTO orderDTO =  orderService.findone(orderid);
        OrderDTO result =  orderService.finish(orderDTO);

        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    public void paid() {

        OrderDTO orderDTO =  orderService.findone(orderid);
        OrderDTO result =  orderService.paid(orderDTO);

        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());

    }
}