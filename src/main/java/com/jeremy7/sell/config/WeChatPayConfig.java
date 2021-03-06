package com.jeremy7.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WeChatPayConfig {

    @Autowired
    private  WechatAccoutConfig wechatAccoutConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){

        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccoutConfig.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAccoutConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(wechatAccoutConfig.getKeyPath());
        wxPayH5Config.setMchId(wechatAccoutConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccoutConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAccoutConfig.getNotifyUrl());



        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);

        return  bestPayService;

    }




}
