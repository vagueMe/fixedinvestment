package com.my.coin.fixedinvestment.controller;

import com.my.coin.fixedinvestment.Result;
import com.my.coin.fixedinvestment.service.TradeRecodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author hudi
 * @date 2021-07-14 15:04
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TradeRecodeService tradeRecodeService;

    @GetMapping("/test1")
    public Result<?> test(){
        tradeRecodeService.createdSpotOrder();
        return Result.ok("定投成功");
    }


    public static void main(String[] args) {
        String whir2011 = getMD5Str("whir2011");
        System.out.println("whir2011 = " + whir2011);
    }

    public static String getMD5Str(String str) {
        byte[] digest = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //16是表示转换为16进制数
        return new BigInteger(1, digest).toString(16).toUpperCase();
    }
}
