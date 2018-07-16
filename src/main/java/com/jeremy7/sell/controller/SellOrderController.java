package com.jeremy7.sell.controller;

import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/*
* 卖家端订单
*
* */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){

        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOpage = orderService.findList(pageRequest);

        map.put("orderDTOpage",orderDTOpage);
        map.put("currentPage", page);
        map.put("size",size);

        return  new ModelAndView("pay/order/list",map);

        }

        @GetMapping("/cancel")
        public ModelAndView cancel(@RequestParam("orderId") String orderId,
                                   Map<String,Object> map){

        OrderDTO orderDTO = orderService.findone(orderId);
        if(orderDTO == null){

            log.error("【买家端取消订单】查询不到订单");
            map.put("msg",ResultEnum.ORDER_NOT_EXIST.getMsg());
            map.put("url","/sell/seller/order/list");
            return  new ModelAndView("common/error",map);
        }
         orderService.cancel(orderDTO);

                return  null;
        }






}
