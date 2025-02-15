package com.owpai.server.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 创建预支付订单
     *
     * @param outTradeNo     商户订单号
     * @param totalAmount    订单总金额
     * @param subject        订单标题
     * @param storeId        商户门店编号
     * @param timeoutExpress 订单允许的最晚付款时间
     * @return 支付宝预下单响应
     */
    public AlipayTradePrecreateResponse createPrePayOrder(String outTradeNo,
                                                         String totalAmount,
                                                         String subject,
                                                         String storeId,
                                                         String timeoutExpress) throws AlipayApiException {
        // 创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        
        // 构建请求参数
        String bizContent = String.format("{"
            + "\"out_trade_no\":\"%s\","
            + "\"total_amount\":\"%s\","
            + "\"subject\":\"%s\","
            + "\"store_id\":\"%s\","
            + "\"timeout_express\":\"%s\""
            + "}", outTradeNo, totalAmount, subject, storeId, timeoutExpress);

        request.setBizContent(bizContent);
        
        // 调用支付宝接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        
        // 记录响应日志
        log.info("支付宝预下单响应：{}", response.getBody());
        
        return response;
    }
}