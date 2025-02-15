package com.owpai.server.service;

import com.owpai.pojo.entity.Orders;

public interface EmailService {
    /**
     * 发送订单通知邮件
     * @param order 订单信息
     * @return 是否发送成功
     */
    boolean sendOrderNotification(Orders order);

    /**
     * 发送通用邮件
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return 是否发送成功
     */
    boolean sendEmail(String to, String subject, String content);

    /**
     * 发送卡密信息
     * @param order 订单信息
     * @param cardKey 卡密信息
     * @return 是否发送成功
     */
    boolean sendCardKeyInfo(Orders order, String cardKey);

    /**
     * 发送普通文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return 是否发送成功
     */
    boolean sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML格式邮件
     * @param to 收件人
     * @param subject 主题
     * @param content HTML内容
     * @return 是否发送成功
     */
    boolean sendHtmlMail(String to, String subject, String content);
}