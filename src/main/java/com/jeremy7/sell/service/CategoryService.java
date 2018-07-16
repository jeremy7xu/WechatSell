package com.jeremy7.sell.service;

import com.jeremy7.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

     ProductCategory findOne(Integer categoryId);

     List<ProductCategory> findAll();
     /*买家端*/
     List<ProductCategory>  findByCategoryTypeIn(List<Integer> categoryTypeList);

     ProductCategory save(ProductCategory productCategory);
}
