package com.jeremy7.sell.service.impl;

import com.jeremy7.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoImplTest {

    @Autowired
    private ProductInfoImpl service;

    @Test
    public void findOne() {


      ProductInfo productInfos =  service.findOne("7");

        Assert.assertEquals("7",productInfos.getProductId());

    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void findAll() {

        /*分页*/
        PageRequest pageRequest = PageRequest.of(0,2);

        Page<ProductInfo> page = service.findAll(pageRequest);

        System.out.println(page.getTotalElements());
    }

    @Test
    public void save() {
    }
}