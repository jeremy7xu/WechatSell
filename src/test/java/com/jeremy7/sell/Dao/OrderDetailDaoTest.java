package com.jeremy7.sell.Dao;

import com.jeremy7.sell.dataobject.OrderDetail;
import jdk.internal.dynalink.linker.LinkerServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    OrderDetailDao dao;

    @Test
    public  void  save(){

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setDetailId("1234");
        orderDetail.setOrderId("123");
        orderDetail.setProductIcon("http://####");
        orderDetail.setProductId("7");
        orderDetail.setProductName("柠檬水");
        orderDetail.setProductPrice(new BigDecimal(1.5));
        orderDetail.setProductQuantity(1);

       OrderDetail orderDeta =  dao.save(orderDetail);

        Assert.assertNotNull(orderDeta);

    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> list = dao.findByOrderId("123");
        Assert.assertNotEquals(0,list.size());

    }
}