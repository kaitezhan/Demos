package com.boneix.base.mail;

import org.apache.commons.collections.MapUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by rzhang on 2017/7/3.
 */
public class HtmlVelocityEngine {
    private static final Logger logger = LoggerFactory.getLogger(HtmlVelocityEngine.class);

    private VelocityEngine velocityEngine;

    public String customizeMailHtmlText(String filePath, Map<String, Object> model) {
        if (MapUtils.isEmpty(model)) {
            logger.warn("When customize Mail HtmlText ,model is empty");
            return null;
        }
        VelocityContext context = new VelocityContext();
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate(filePath, MailEncodeEnum.UTF8.getValue(), context, stringWriter);
        return stringWriter.toString();
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
