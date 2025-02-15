package com.owpai.server.service.impl;

import com.owpai.pojo.entity.Orders;
import com.owpai.server.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendOrderNotification(Orders order) {
        String subject = "订单通知 - 订单号: " + order.getOrderNum();
        String content = String.format("您的订单已创建成功！\n订单号：%s\n商品数量：%s\n总金额：%s",
                order.getOrderNum(), order.getNumber(), order.getTotalPrice());
        return sendEmail(order.getEmail(), subject, content);
    }

    @Override
    public boolean sendEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("邮件发送成功：{}", to);
            return true;
        } catch (Exception e) {
            log.error("邮件发送失败：{}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendCardKeyInfo(Orders order, String cardKey) {
        String subject = "卡密信息 - 订单号: " + order.getOrderNum();
        String content = String.format("您的订单卡密信息已生成！\n订单号：%s\n卡密信息：%s",
                order.getOrderNum(), cardKey);
        return sendEmail(order.getEmail(), subject, content);
    }

    @Override
    public boolean sendSimpleMail(String to, String subject, String content) {
        return sendEmail(to, subject, content);
    }

    @Override
    public boolean sendHtmlMail(String to, String subject, String content) {
        // TODO: 实现HTML格式邮件发送
        return false;
    }
}