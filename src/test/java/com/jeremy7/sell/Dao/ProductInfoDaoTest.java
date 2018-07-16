package com.jeremy7.sell.Dao;

import com.jeremy7.sell.dataobject.ProductInfo;
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
public class ProductInfoDaoTest {

    @Autowired
    private  ProductInfoDao dao;

    @Test
    public void findByProductStatus() {
      List<ProductInfo> list =  dao.findByProductStatus(0);
        Assert.assertNotEquals(0,list.size()) ;
    }

    @Test
    public  void  save(){
        ProductInfo productInfo =  new ProductInfo();

        productInfo.setProductId("7");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("很好吃");
        productInfo.setProductName("棒棒冰");
        productInfo.setProductPrice(new BigDecimal(17));
        productInfo.setProductStock(100);
        productInfo.setProductIcon("http://saasa");
        productInfo.setProductStatus(0);


      ProductInfo result =   dao.save(productInfo);

      System.out.println(result);


    }
}