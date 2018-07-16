package com.jeremy7.sell.dataobject;

import com.jeremy7.sell.enums.OrderStatusEnum;
import com.jeremy7.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class OrderMaster {

    @Id
    private  String orderId;

    private  String buyerName;

    private  String buyerPhone;

    private  String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    /*订单状态， 默认为0 新订单*/
    private  Integer orderStatus=OrderStatusEnum.NEW.getCode();

    /*支付状态， 默认为0，未支付*/
    private  Integer payStatus = PayStatusEnum.WAIT.getCode();

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private  Date updateTime;



}
