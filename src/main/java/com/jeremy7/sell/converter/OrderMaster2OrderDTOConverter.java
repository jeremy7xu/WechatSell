package com.jeremy7.sell.converter;

import com.jeremy7.sell.dataobject.OrderMaster;
import com.jeremy7.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

    private  static OrderDTO convert(OrderMaster orderMaster){

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){


    //lamda 表达式
        return  orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());

    }

}
