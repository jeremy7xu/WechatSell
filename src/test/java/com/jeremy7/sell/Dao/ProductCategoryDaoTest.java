package com.jeremy7.sell.Dao;

import com.jeremy7.sell.dataobject.ProductCategory;
import javafx.geometry.VPos;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private  ProductCategoryDao dao;

    @Test
    public void  findoneTest(){

      ProductCategory productCategory = dao.findById(1).get();
        System.out.println(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);

         List<ProductCategory> lists =  dao.findByCategoryTypeIn(list);

        /*  Assert.assertNotEquals(0,lists.size());*/
        System.out.println("ssadada111121");
        System.out.println(lists.size());

    }

}