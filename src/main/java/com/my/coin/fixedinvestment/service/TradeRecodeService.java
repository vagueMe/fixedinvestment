package com.my.coin.fixedinvestment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.my.coin.fixedinvestment.entity.TradeRecode;

/**
 * @author hudi
 * @date 2021-07-13 15:53
 */
public interface TradeRecodeService  extends IService<TradeRecode> {

    void insertData();

    void createdSpotOrder();


}
