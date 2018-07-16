package com.jeremy7.sell.service;

import com.jeremy7.sell.dataobject.ProductInfo;

import com.jeremy7.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface productService {

    ProductInfo findOne(String productId);

    /*查询上架商品*/
    List<ProductInfo> findUpAll();

    /*分页参数*/
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo Save(ProductInfo productInfo);

    /*加库存*/

    void increaseStock(List<CartDTO> cartDTOList);



    /*减库存*/

    void decreaseStock(List<CartDTO> cartDTOList);





}
