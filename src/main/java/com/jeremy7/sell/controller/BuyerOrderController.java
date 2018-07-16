package com.jeremy7.sell.controller;

import com.jeremy7.sell.VO.ResultVO;
import com.jeremy7.sell.converter.OrderForm2OrderDTOConverter;
import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.form.OrderForm;
import com.jeremy7.sell.service.BuyerService;
import com.jeremy7.sell.service.OrderService;
import com.jeremy7.sell.utils.ResultVoUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableScheduledFuture;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    BuyerService buyerService;




    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        // 验证表单是否出现错误
        if(bindingResult.hasErrors()){

            log.error("【创建订单】 参数不正确， orderForm={}", orderForm);

            throw  new SellException(ResultEnum.PARAM_ERROR.getCode(),
                                     bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){

            log.error("【创建订单】 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);

        }

       OrderDTO createResult =  orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();

        map.put("orderId",createResult.getOrderId());

        return ResultVoUtils.success(map);

    }



    //订单列表
    public  ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                          @RequestParam(value ="page", defaultValue = "0") String page,
                                          @RequestParam(value = "size",defaultValue = "10") String size){

            if(StringUtils.isEmpty(openid)){
                log.error("【查询订单列表】 openid为空");
                throw  new SellException(ResultEnum.PARAM_ERROR);

            }

            Page<OrderDTO> orderDTOpage =  orderService.findList(openid,PageRequest.of(Integer.valueOf(page),Integer.valueOf(size)));

            return ResultVoUtils.success(orderDTOpage.getContent());




    }


    // 订单详情

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){

      OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);

        return ResultVoUtils.success(orderDTO);


    }


    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,@RequestParam("orderId") String orderId){

        buyerService.cancelOrder(openid,orderId);

        return  ResultVoUtils.success();






    }


}
