package com.jeremy7.sell.controller;

import com.jeremy7.sell.VO.ProductInfoVO;
import com.jeremy7.sell.VO.ProductVO;
import com.jeremy7.sell.VO.ResultVO;
import com.jeremy7.sell.dataobject.ProductCategory;
import com.jeremy7.sell.dataobject.ProductInfo;
import com.jeremy7.sell.service.CategoryService;
import com.jeremy7.sell.service.productService;
import com.jeremy7.sell.utils.ResultVoUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
* 买家商品
*
*
* */
/*返回jason格式*/
@RestController
@RequestMapping("/buyer/product")
public class BuyerProduct {

    @Autowired
    private productService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ResultVO list(){

        // 1, 查询所有上架商品
       List<ProductInfo>  productInfoList = productService.findUpAll();

    /*    //2, 查询类目（一次性查询）

       List<Integer> categoryTypeList = new ArrayList<>();

       //传统方法

        for (ProductInfo productInfo : productInfoList) {
               categoryTypeList.add( productInfo.getCategoryType());
        }*/
        // 精简方法 java8特性 lambda表达式
       List<Integer> categoryTypeList = productInfoList.stream()
               .map(e -> e.getCategoryType())
               .collect(Collectors.toList());

        List<ProductCategory> productCategoryList =  categoryService.findByCategoryTypeIn(categoryTypeList);

        //数据拼装
        List<ProductVO> productVOList = new ArrayList<>();

        for (ProductCategory productCategory : productCategoryList) {
           ProductVO productVO = new ProductVO();
           productVO.setCategorytype(productCategory.getCategoryType());
           productVO.setCategoryname(productCategory.getCategoryName());


            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                  ProductInfoVO productInfoVO =  new ProductInfoVO();
                  //使用benautils
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setFoods(productInfoVOList);
            productVOList.add(productVO);
        }

   /*   ResultVO vo = new ResultVO();
      vo.setCode(0);
      vo.setMsg("成功");

     vo.setData(productVOList);*/
     return ResultVoUtils.success(productVOList);


    }


}
