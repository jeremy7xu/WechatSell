package com.jeremy7.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremy7.sell.dataobject.OrderDetail;
import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.form.OrderForm;
import jdk.internal.org.objectweb.asm.commons.TryCatchBlockSorter;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTOConverter {

    public   static OrderDTO convert(OrderForm orderForm){

        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        // 不用beanutils的原因是 orderDTO和orderForm 中的字段名字不一样

        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList =   gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){

            log.error("【格式转换】 错误， String=｛｝", orderForm.getItems());

            throw  new SellException(ResultEnum.PARAM_ERROR);
        }


        orderDTO.setOrderDetailList(orderDetailList);

        return  orderDTO;
    }


}
