package com.my.coin.fixedinvestment.config;

import lombok.Data;

/**
 * @author hudi
 * @date 2021-07-13 17:45
 */
@Data
public class BianApi {
    public static String baseUrl = "https://api.binance.com";

    public static String  orderTest = baseUrl + "/api/v3/order/test";

    public static String  test = baseUrl + "/sapi/v1/bswap/pools";

    public static String  order = baseUrl +"/api/v3/order";

    public static String  account = "https://fapi.binance.com" +"/fapi/v2/account";





}
