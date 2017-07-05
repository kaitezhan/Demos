package com.boneix.base.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 发送邮件工具类
 * Created by rzhang on 2017/7/3.
 */
public class MailSender {

    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private JavaMailSender javaMailSender;

    private String fromAddress;

    /**
     * 发送网页文本类型的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param htmlText 网页文本
     */
    public void sendEmailWithHtml(String to, String subject, String htmlText) {
        try {
            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, MailEncodeEnum.UTF8.getValue());
            // 设置发送人
            helper.setFrom(this.fromAddress);
            // 设置接收人
            helper.setTo(to);
            // 设置主题
            helper.setSubject(subject);
            // 设置文本
            helper.setText(htmlText, true);
            logger.debug("Attempt to send Email to {}", to);
            javaMailSender.send(message);
            logger.debug("Already send Email to {}", to);
        } catch (Exception e) {
            logger.error("When attempt to send Email ,Exception happened.{}", e);
        }
    }

    /**
     * 发送带附件的网页文本类型的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param htmlText 网页文本
     * @param fileList 附件
     */
    public void sendEmailWithAttachment(String to, String subject, String htmlText, Map<String, InputStreamSource> fileList) {
        try {
            MimeMessage message = this.javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, MailEncodeEnum.UTF8.getValue());
            // 设置发送人
            helper.setFrom(this.fromAddress);
            // 设置接收人
            helper.setTo(to);
            // 设置主题
            helper.setSubject(subject);
            // 设置文本
            helper.setText(htmlText, true);
            // 设置附件
            for (Map.Entry<String, InputStreamSource> entry : fileList.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
            logger.debug("Attempt to send Email to {}", to);
            javaMailSender.send(message);
            logger.debug("Already send Email to {}", to);
        } catch (Exception e) {
            logger.error("When attempt to send Email ,Exception happened.{}", e);
        }
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

}
