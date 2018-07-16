package com.jeremy7.sell.Dao;

import com.jeremy7.sell.dataobject.ProductCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductCategoryDao  extends JpaRepository<ProductCategory,Integer> {

    @Override
    <S extends ProductCategory> Optional<S> findOne(Example<S> example);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
