package com.my.coin.fixedinvestment.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.coin.fixedinvestment.config.BianApi;
import com.my.coin.fixedinvestment.config.UserInfo;
import com.my.coin.fixedinvestment.entity.TradeRecode;
import com.my.coin.fixedinvestment.exception.MyException;
import com.my.coin.fixedinvestment.mapper.TradeRecodeMapper;
import com.my.coin.fixedinvestment.service.TradeRecodeService;
import com.my.coin.fixedinvestment.util.SendDataUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hudi
 * @date 2021-07-13 15:54
 */
@Service
@Log4j2
@Transactional
public class TradeRecodeServiceImpl extends ServiceImpl<TradeRecodeMapper, TradeRecode> implements TradeRecodeService {

    @Autowired
    private SendDataUtil sendDataUtil;

    @Override
    public void insertData() {
        TradeRecode t = new TradeRecode();
        t.setRecodeId(IdUtil.simpleUUID());
        t.setDateString(DateUtil.format(new Date(), "yyyy-MM-dd"));
        t.setSuccess(true);
        t.setResultData("1111111111111");
        t.setCreateTime(new Date());
        this.save(t);
    }

    @Override
    public void createdSpotOrder(){

        String today = DateUtil.format(new Date(), "yyyy-MM-dd");
        TradeRecode recode = new TradeRecode();
        recode.setDateString(today);

        List<TradeRecode> list = this.list(new LambdaQueryWrapper<TradeRecode>().eq(TradeRecode::getDateString, today));
        if(!list.isEmpty()){
            for (TradeRecode tradeRecode : list) {
                if (Boolean.TRUE.equals(tradeRecode.getSuccess())) {
                    throw new MyException("今日已经定投过了");
                }
            }
            recode.setRecodeId(list.get(0).getRecodeId());
            recode.setUpdateTime(new Date());
        }else{
            recode.setCreateTime(new Date());
        }

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
        Map<String, String> result = new HashMap<>();
        try{
           result = sendDataUtil.postSecretBian(BianApi.order, map);
        } catch(Exception e){
            result.put("code","500");
            result.put("msg", e.getMessage());
        }

        recode.setResultData(JSON.toJSONString(result));
        boolean info = false;
        if("200".equals(result.get("code"))){
            recode.setSuccess(true);
            info = true;
        } else {
            recode.setSuccess(false);
        }
        this.saveOrUpdate(recode);
        if(!info){
            throw new MyException("定投失败");
        }
    }


}
