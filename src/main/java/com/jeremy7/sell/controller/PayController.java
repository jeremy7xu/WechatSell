package com.jeremy7.sell.controller;

import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.service.OrderService;
import com.jeremy7.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    OrderService orderService;

    @Autowired
    PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                                Map<String,Object> map){

    //1, 查询订单

       OrderDTO orderDTO =  orderService.findone(orderId);

       if(orderDTO == null){
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
       }

       // 2. 发起支付

       PayResponse payResponse =  payService.create(orderDTO);

       map.put("payResponse",payResponse);
       map.put("returnUrl",returnUrl);

        return  new ModelAndView("/pay/create",map);

        }

        /*微信异步通知*/
        @PostMapping("/notify")
          public  ModelAndView  notify(@RequestBody  String notifyData){

            payService.notify(notifyData);


            //返回给微信处理结果
            return  new ModelAndView("pay/success");
        }



}
