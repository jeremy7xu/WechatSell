package com.jeremy7.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    @Id
    private  String detailId;

    private  String orderId;

    private  String productId;

    private  String productName;

    /*商品单价*/
    private BigDecimal productPrice;

    /*商品数量*/
    private  Integer productQuantity;

    private  String  productIcon;



}
