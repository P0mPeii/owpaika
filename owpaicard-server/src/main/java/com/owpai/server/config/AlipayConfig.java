package com.owpai.server.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.owpai.pojo.entity.PayInfo;
import com.owpai.server.mapper.PayInfoMapper;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AlipayConfig {

    @Autowired
    private PayInfoMapper payInfoMapper;

    private String serverUrl;
    private String appId;
    private String privateKey;
    private String alipayPublicKey;
    private String charset;
    private String format;
    private String signType;
    private String returnUrl;
    private String notifyUrl;

    private void loadConfig() {
        PayInfo payInfo = payInfoMapper.selectOne(null);
        if (payInfo != null) {
            BeanUtils.copyProperties(payInfo, this);
        }
    }

    @Bean
    public AlipayClient alipayClient() {
        loadConfig();
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
