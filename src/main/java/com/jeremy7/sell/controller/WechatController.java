package com.jeremy7.sell.controller;

import com.jeremy7.sell.enums.ResultEnum;
import com.jeremy7.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.RectangularShape;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private  WxMpService wxMpService;

    @RequestMapping("/authorize")
    public  String  authorize(@RequestParam("returnUrl") String returnUrl ){

        //1.配置

        //2.调用方法
        String url= "http://jeremy7.mynatapp.cc/sell/wechat/userinfo";
      String redirectUrl =   wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                                                                        URLEncoder.encode(returnUrl));
       log.info("【微信网页授权】获取code，result={}",redirectUrl);

       return "redirect:" + redirectUrl ;

    }

    @RequestMapping("/userinfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try {
            wxMpOAuth2AccessToken =   wxMpService.oauth2getAccessToken(code);

        } catch (WxErrorException e){

            log.error("【微信网页授权】 {}", e);
            throw  new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());

        }

       String openId =  wxMpOAuth2AccessToken.getOpenId();

        return "redirect:" + returnUrl + "?openid=" + openId;








    }



}
