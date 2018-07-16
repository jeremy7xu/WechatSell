package com.jeremy7.sell.dto;

/*
* 在层中传输用的  data transprot object
*
* */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jeremy7.sell.dataobject.OrderDetail;
import com.jeremy7.sell.enums.OrderStatusEnum;
import com.jeremy7.sell.enums.PayStatusEnum;
import com.jeremy7.sell.utils.EnumUtil;
import com.jeremy7.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//控制返回的json字符串显示哪些字段。这里的设置是为null的字段不显示
public class OrderDTO {


    private  String orderId;

    private  String buyerName;

    private  String buyerPhone;

    private  String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /*订单状态， 默认为0 新订单*/
    private  Integer orderStatus;

    /*支付状态， 默认为0，未支付*/
    private  Integer payStatus;

    /*创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /*更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private  Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public  OrderStatusEnum getOrderStatusEnum(){

      return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);


    }
    @JsonIgnore
    public  PayStatusEnum getPayStatusEnum(){


        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);

    }

}
