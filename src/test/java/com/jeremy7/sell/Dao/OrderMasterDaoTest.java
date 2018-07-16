package com.jeremy7.sell.Dao;

import com.jeremy7.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.PrintConversionEvent;
import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {


    @Autowired
    OrderMasterDao dao;

    private  final  String OPENID="111";

    @Test
   public  void save(){

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234");
        orderMaster.setBuyerOpenid("115");
        orderMaster.setBuyerName("rechal");
        orderMaster.setBuyerAddress("sasaqw");
        orderMaster.setBuyerPhone("88874454");
        orderMaster.setOrderAmount(new BigDecimal(2.5));

      OrderMaster oa = dao.save(orderMaster);

        System.out.println(oa);


    }

    @Test
    public void findByBuyerOpenid() {


       Page<OrderMaster> result =  dao.findByBuyerOpenid(OPENID,PageRequest.of(0,2));

        System.out.println(result.getTotalElements());
    }
}