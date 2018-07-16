package com.jeremy7.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/*
* 商品（包含类目）
*
* */
@Data
public class ProductVO {

    /*类目名字*/
    @JsonProperty("name")
    private  String categoryname ;

    @JsonProperty("type")
    private  Integer categorytype;

    @JsonProperty("foods")
    private List<ProductInfoVO> foods;
}
