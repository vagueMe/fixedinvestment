package com.my.coin.fixedinvestment.service.impl;

import com.my.coin.fixedinvestment.config.BianApi;
import com.my.coin.fixedinvestment.config.UserInfo;
import com.my.coin.fixedinvestment.exception.MyException;
import com.my.coin.fixedinvestment.service.TradeRecodeService;
import com.my.coin.fixedinvestment.util.SendDataUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hudi
 * @date 2021-07-13 15:54
 */
@Service
@Log4j2
public class TradeRecodeServiceImpl implements TradeRecodeService {

    @Autowired
    private SendDataUtil sendDataUtil;


    @Override
    public void createdSpotOrder(){


        Map<String,Object> map =  new HashMap<>();
       /* if (Double.parseDouble(today.split("-")[2])%2 == 0){ // 偶数天
            map.put("symbol", UserInfo.SYMBOBTCL);
        }else{
            map.put("symbol", UserInfo.SYMBOL);
        }*/

        map.put("symbol", UserInfo.SYMBOL);

        map.put("quoteOrderQty",UserInfo.QUOTEORDERQTY);
        map.put("side","BUY");
        map.put("type","MARKET");
        Map<String, String> result;
        try{
           result = sendDataUtil.postSecretBian(BianApi.order, map);
        } catch(Exception e){
            throw new MyException("定投失败");
        }

        if(!"200".equals(result.get("code"))){
            throw new MyException("定投失败");
        }
    }


}
