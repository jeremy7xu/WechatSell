package com.jeremy7.sell.service.impl;

import com.jeremy7.sell.Dao.OrderDetailDao;
import com.jeremy7.sell.Dao.OrderMasterDao;
import com.jeremy7.sell.converter.OrderMaster2OrderDTOConverter;
import com.jeremy7.sell.dataobject.OrderDetail;
import com.jeremy7.sell.dataobject.OrderMaster;
import com.jeremy7.sell.dataobject.ProductInfo;
import com.jeremy7.sell.dto.CartDTO;
import com.jeremy7.sell.dto.OrderDTO;
import com.jeremy7.sell.enums.OrderStatusEnum;
import com.jeremy7.sell.enums.PayStatusEnum;
import com.jeremy7.sell.enums.ProductStatus;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.service.OrderService;
import com.jeremy7.sell.service.productService;
import com.jeremy7.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.print.PeekGraphics;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


        @Autowired
        private OrderDetailDao  detailDao;

        @Autowired
        private productService productService;

        @Autowired
        private  OrderMasterDao orderMasterDao;


        /*创建订单*/
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {


        String orderId = KeyUtil.getUniqueKey();

        BigDecimal Orderamount = new BigDecimal(0);
        //1, 查询商品（数量，价格） 看库存是否足够


        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
           ProductInfo productInfo =  productService.findOne(orderDetail.getProductId());

           if(productInfo == null){

               throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
            // 2, 计算订单总价
           Orderamount =  productInfo.getProductPrice()
                          .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                          .add(Orderamount);
            // 订单详情入库

            /*设置id*/
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);

            /*通过API 文档  前端只传过来商品ID 和 商品数量*/

            BeanUtils.copyProperties(productInfo,orderDetail);

            detailDao.save(orderDetail);

        }



        //3, 写入订单数据库（ordermaster和 orderdetail）
        /*orderdetail已经写入了 */

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //先拷贝，然后在设置值，否则会被覆盖掉
        // 注意 orderstatus 和 paystatus
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMaster.setOrderAmount(Orderamount);



        orderMasterDao.save(orderMaster);


        //4， 扣库存

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);


        return orderDTO;
    }

    /*查询订单*/
    @Override
    public OrderDTO findone(String orderId) {

       OrderMaster orderMaster =  orderMasterDao.findById(orderId).get();
        if(orderMaster == null){

            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
      List<OrderDetail> orderDetailList =   detailDao.findByOrderId(orderId);
        if(orderDetailList ==null){

            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /*买家查询订单列表*/
    @Override
    public Page<OrderDTO> findList(String BuyerOpenid, Pageable pageable) {
        // 不需要查详情
       Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(BuyerOpenid,pageable);

       // 使用自定义转换器
       List<OrderDTO> orderDTOList =  OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

       Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    /*取消订单*/
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();




        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){

            log.error("【取消订单】，订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());

            throw  new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态, 先修改DTO，然后再copy
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult =  orderMasterDao.save(orderMaster);
        if(updateResult == null){

            log.error("【取消订单】更新失败，orderMaster={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILED);
        }

        // 返还库存,先判断有没有商品
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw  new SellException(ResultEnum.ORDER_DETAIL_EMPTY);

        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
      productService.increaseStock(cartDTOList);



        // 如果已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
            //写TODO的好处是 在ide左下角可以快速找到

        }

        return orderDTO;
    }
    /*完成订单*/
    @Override
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确，orderid={},  orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());

            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);

        if(updateResult == null){

            log.error("【完结订单】更新失败，orderMaster={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILED);
        }

        return orderDTO;
    }
    /*支付订单*/
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        // 判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 订单状态不正确，orderid={},  orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());

            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改支付状态
        if(orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){

            log.error("【订单支付完成】 订单支付状态不正确， orderDTO={}",orderDTO);

            throw  new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);

        }


        // 修改支付状态

        orderDTO.setOrderStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);

        if(updateResult == null){

            log.error("【订单支付完成】更新失败，orderMaster={}",updateResult);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILED);
        }

        return orderDTO;


    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

       Page<OrderMaster> orderMasterPage =  orderMasterDao.findAll(pageable);

        List<OrderDTO> orderDTOList =  OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());


        return orderDTOPage;
    }
}
