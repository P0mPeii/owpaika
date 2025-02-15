package com.owpai.server.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AlipayConfig {

    @Value("${alipay.server-url}")
    private String serverUrl;

    @Value("${alipay.app-id}")
    private String appId;

    @Value("${alipay.alipay-private-key}")
    private String privateKey;

    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;

    @Value("${alipay.charset:UTF-8}")
    private String charset;

    @Value("${alipay.format:json}")
    private String format;

    @Value("${alipay.sign-type:RSA2}")
    private String signType;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                serverUrl,
                appId,
                privateKey,
                format,
                charset,
                alipayPublicKey,
                signType
        );
    }
}
