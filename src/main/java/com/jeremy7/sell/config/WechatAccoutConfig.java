package com.jeremy7.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccoutConfig {

    private  String mpAppId;

    private  String mpAppSecret;

    /*商户号*/
    private String mchId;

    /*商户秘钥*/
    private String mchKey;

    /*商户证书路径*/
    private String keyPath;

    /*异步通知地址*/
    private String notifyUrl;


}
