package com.boneix.base.mail.test;

import com.boneix.base.mail.HtmlVelocityEngine;
import com.boneix.base.mail.MailSender;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by rzhang on 2017/7/3.
 */
public class DemoTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(DemoTest.class);

    @Resource
    private MailSender mailSender;

    @Resource
    private HtmlVelocityEngine htmlVelocityEngine;

    @Test
    public void demoTest() {
    }

    @Test
    public void demoTestSendEmail() {
        String filePath = "demo.vm";
        Map<String, Object> model = new HashedMap();
        model.put("userName", "rzhang@mo9.com");
        String htmlText = htmlVelocityEngine.customizeMailHtmlText(filePath, model);
        String toAddress = "rzhang@mo9.com";
        String subject = "发送邮件内容为html模板";
        mailSender.sendEmailWithHtml(toAddress, subject, htmlText);
    }


}
