package com.jeremy7.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    @Id
    private  String productId;
    /*名字*/
    private  String productName;

    /*单价*/
    private BigDecimal productPrice;

    /*库存*/
    private  Integer productStock;

    /*描述*/
    private String productDescription;

    /*小图*/
    private  String productIcon;

    /*商品状态*/
    private  Integer productStatus;

    /*类目编号*/
    private  Integer categoryType;






}
