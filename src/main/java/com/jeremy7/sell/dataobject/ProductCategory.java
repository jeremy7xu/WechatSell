package com.jeremy7.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.security.PrivateKey;

/*JPA自动中间加下划线*/
@Entity
@DynamicUpdate // 动态更新  uptimedate
@Data // 自动生成getter setter tostring放法  也可以只用@getter
public class ProductCategory {

    /*类目id*/
    @Id
    @GeneratedValue
    private Integer categoryId;
    /*类目名字*/
    private String  categoryName;
    /*类目编号*/
    private  Integer categoryType;

    public  ProductCategory(){}


}
