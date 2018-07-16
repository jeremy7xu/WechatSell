package com.jeremy7.sell.service.impl;

import com.jeremy7.sell.Dao.ProductInfoDao;
import com.jeremy7.sell.dataobject.ProductInfo;
import com.jeremy7.sell.dto.CartDTO;
import com.jeremy7.sell.enums.ProductStatus;
import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import com.jeremy7.sell.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Service
public class ProductInfoImpl implements productService {

    @Autowired
    private ProductInfoDao dao;

    @Override
    public ProductInfo findOne(String productId) {
        return dao.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return dao.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public ProductInfo Save(ProductInfo productInfo) {
        return dao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = dao.findById(cartDTO.getProductId()).get();
            if(productInfo == null){

                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);

            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            dao.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {

        for (CartDTO cartDTO : cartDTOList) {

           ProductInfo productInfo =  dao.findById(cartDTO.getProductId()).get();

           if(productInfo == null){

               throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);

           }

           Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();

           if(result < 0){

               throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);

           }

           productInfo.setProductStock(result);

           dao.save(productInfo);

        }

    }
}
