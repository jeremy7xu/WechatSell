package com.jeremy7.sell.dto;

import lombok.Data;

@Data
public class CartDTO {

    /*商品ID*/
    private  String productId;

    /*商品数量*/
    private  Integer  productQuantity;


    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
