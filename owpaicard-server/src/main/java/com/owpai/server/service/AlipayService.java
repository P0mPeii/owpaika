package com.owpai.server.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.owpai.server.config.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayConfig alipayConfig;

    /**
     * 创建预支付订单
     *
     * @param outTradeNo  商户订单号
     * @param totalAmount 订单总金额
     *                    subject 订单标题
     * @return 支付宝预下单响应
     */
    public AlipayTradePrecreateResponse createPrePayOrder(String outTradeNo,
            String totalAmount,
            String subject,
            String timeoutExpress) throws AlipayApiException {
        // 创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        // 设置支付完成后的跳转地址
        String returnUrl = String.format("%s/detail-order-sn/%s", alipayConfig.getReturnUrl(), outTradeNo);
        request.setReturnUrl(returnUrl);

        // 设置异步通知地址
        request.setNotifyUrl(alipayConfig.getNotifyUrl());

        // 构建请求参数
        String bizContent = String.format("{"
                + "\"out_trade_no\":\"%s\","
                + "\"total_amount\":\"%s\","
                + "\"subject\":\"%s\","
                + "\"timeout_express\":\"%s\""
                + "}", outTradeNo, totalAmount, subject, timeoutExpress);

        request.setBizContent(bizContent);

        // 调用支付宝接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        // 记录响应日志
        log.info("支付宝预下单响应：{}", response.getBody());

        return response;
    }
}