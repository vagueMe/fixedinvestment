package com.my.coin.fixedinvestment.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.coin.fixedinvestment.config.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hudi
 * @date 2021-07-14 11:24
 */
@Component
@Log4j2
public class SendDataUtil {

    @Autowired
    private  RestTemplate restTemplate;
    /**
     * 将发送的post请求中的参数转为utf-8编码
     *
     */
    public  Map<String,Object> postBian(String url, Map<String, Object> param) {
        Map<String ,Object> resultMap = new HashMap<>();
        resultMap.put("method","getSecrectBian");
        resultMap.put("code",200);
        resultMap.put("result","");
        resultMap.put("url",url);

        param.put("timestamp", new Date().getTime());
        String paramTemp = JSONObject.toJSONString(param);
        resultMap.put("body",paramTemp);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("X-MBX-APIKEY", UserInfo.APPKEY);
        HttpEntity<String> formEntity = new HttpEntity<>(paramTemp, headers);
        try {
            String result = restTemplate.postForObject(url, formEntity, String.class);
            resultMap.put("result",result);
        }catch (Exception e){
            resultMap.put("error",e.getMessage());
            resultMap.put("code",500);
        }
        return resultMap;
    }

    public  Map<String,String> postSecretBian(String url, Map<String, Object> param) {
        Map<String ,String> resultMap = new HashMap<>();
        resultMap.put("method","getSecrectBian");
        resultMap.put("code","200");
        resultMap.put("result","");


        param.put("timestamp", new Date().getTime());
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<String, Object> objectObjectEntry : param.entrySet()) {
            temp.append(objectObjectEntry.getKey()).append("=").append(objectObjectEntry.getValue()).append("&");
        }
        temp = new StringBuilder(temp.substring(0, temp.length() - 1));
        String secret;
        try {
            secret = SecretUtil.hmacSHA256(temp.toString(), UserInfo.APPSECRET);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            resultMap.put("error",e.getMessage());
            resultMap.put("code","500");
            return resultMap;
        }
        param.put("signature", secret);
        String paramTemp = JSONObject.toJSONString(param);
        resultMap.put("body",paramTemp);
        temp.append("&signature=").append(secret);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("X-MBX-APIKEY", UserInfo.APPKEY);
        HttpEntity<String> formEntity = new HttpEntity<>("", headers);
        try {
            url = url + "?" + temp;
            resultMap.put("url",url);
            String result = restTemplate.postForObject(url, formEntity, String.class);
            resultMap.put("result",result);
        }catch (Exception e){
            resultMap.put("error",e.getMessage());
            resultMap.put("code","500");
        }
        return resultMap;
    }

    public  Map<String,Object> getBian(String url, Map<String, Object> param) {

        Map<String ,Object> resultMap = new HashMap<>();
        resultMap.put("method","getSecrectBian");
        resultMap.put("code",200);
        resultMap.put("result","");


        param.put("timestamp", new Date().getTime());
        param.put("X-MBX-APIKEY", UserInfo.APPKEY);
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<String, Object> objectObjectEntry : param.entrySet()) {
            temp.append(objectObjectEntry.getKey()).append("=").append(objectObjectEntry.getValue()).append("&");
        }
        temp = new StringBuilder(temp.substring(0, temp.length() - 1));

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("X-MBX-APIKEY", UserInfo.APPKEY);

        HttpEntity<String> formEntity = new HttpEntity<>("", headers);
        try{
            url = url + "?" + temp;
            resultMap.put("url",url);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, formEntity, String.class);
            resultMap.put("result",exchange.getBody());
        } catch(Exception e){
            resultMap.put("error",e.getMessage());
            resultMap.put("code",500);
        }
        return resultMap;
    }

    public  Map<String,Object> getSecrectBian(String url, Map<String, Object> param) throws InvalidKeyException, NoSuchAlgorithmException {
        Map<String ,Object> resultMap = new HashMap<>();
        resultMap.put("method","getSecrectBian");
        resultMap.put("code",200);
        resultMap.put("result","");

        param.put("timestamp", new Date().getTime());
        log.info(param.get("timestamp").toString());
        StringBuilder temp = new StringBuilder();
        for (Map.Entry<String, Object> objectObjectEntry : param.entrySet()) {
            temp.append(objectObjectEntry.getKey()).append("=").append(objectObjectEntry.getValue()).append("&");
        }
        temp = new StringBuilder(temp.substring(0, temp.length() - 1));
        log.info(temp.toString());
        String secret = SecretUtil.hmacSHA256(temp.toString(), UserInfo.APPSECRET);
        param.put("signature", secret);

        temp.append("&signature=").append(secret);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("X-MBX-APIKEY", UserInfo.APPKEY);
//        String forObject = restTemplate.getForObject("https://api.binance.com", String.class);
//        log.info("测试连接----"+forObject);
        try{
            url = url + "?" + temp;
            resultMap.put("url",url);
            HttpEntity<String> formEntity = new HttpEntity<>("", headers);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, formEntity, String.class);
            resultMap.put("result",exchange.getBody());
        } catch(Exception e){
            resultMap.put("error",e.getMessage());
            resultMap.put("code",500);
        }
        return resultMap;


    }

    public static void main(String[] args) {
       String a =  "{\"symbol\":\"ETHUSDT\",\"orderId\":5036190694,\"orderListId\":-1,\"clientOrderId\":\"Wc6IsSOa1KgQpGdLJKQ6YD\",\"transactTime\":1626462002491,\"price\":\"0.00000000\",\"origQty\":\"0.00521000\",\"executedQty\":\"0.00521000\",\"cummulativeQuoteQty\":\"9.99179010\",\"status\":\"FILLED\",\"timeInForce\":\"GTC\",\"type\":\"MARKET\",\"side\":\"BUY\",\"fills\":[{\"price\":\"1917.81000000\",\"qty\":\"0.00521000\",\"commission\":\"0.00000521\",\"commissionAsset\":\"ETH\",\"tradeId\":520682147}]}";
        JSONObject jsonObject = JSON.parseObject(a);
        System.out.println("jsonObject = " + jsonObject);

    }


}
