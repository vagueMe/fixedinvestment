package com.my.coin.fixedinvestment.scheduling;

import com.my.coin.fixedinvestment.service.TradeRecodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author hudi
 * @date 2021-07-13 17:12
 */
@Component
@Log4j2
public class TradeRecordSh {

    @Autowired
    private TradeRecodeService tradeRecodeService;

    // 0点开始每3小时执行一次
    @Scheduled(cron = "0 0 0/2 * * ? ")
    public void execute() {
        tradeRecodeService.createdSpotOrder();
    }
}
