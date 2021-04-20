package com.wsn.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 2017/9/2
 * Time: 15:12
 * Description:
 */
@Component
public class SendSimpleEmail {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendSimpleEmail.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment environment;
    @Autowired
    private SpringTemplateEngine templateEngine;


    /**
     * 发送模板邮件
     * @param subject
     * @param platformName
     * @param serviceHost
     * @param serverName
     * @throws Exception
     */
    public void sendTemplateEmail(String subject, String platformName, String serviceHost, String serverName) throws Exception{
        String toMailAccountStr = environment.getProperty("mail.username");
        String[] toMailAccounts = toMailAccountStr.split(",");
        String fromMailAccount = environment.getProperty("spring.mail.username");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromMailAccount);
            mimeMessageHelper.setTo(toMailAccounts);
            mimeMessageHelper.setSubject(subject);
            Map<String, Object> parameters = new HashMap<>();//动态
            parameters.put("platformName", platformName);
            parameters.put("serviceHost", serviceHost);
            parameters.put("serverName", serverName);

            Context context = new Context();
            for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
                context.setVariable(parameter.getKey(),parameter.getValue());
            }

            String emailContent = templateEngine.process("emailtemplate", context);
            mimeMessageHelper.setText(emailContent,true);
            javaMailSender.send(mimeMessage);

            LOGGER.info("Mail sent successfully");
        } catch (Exception e) {
            LOGGER.error("E-mail failed to send");
            throw e;
        }
    }


    /**
     * 发送简单的通知邮件
     * @param subject
     * @param message
     */
    public void sendSimpleEmail(String subject, String message) throws Exception{
        String toMailAccountStr = environment.getProperty("mail.username");
        String[] toMailAccounts = toMailAccountStr.split(",");
        String fromMailAccount = environment.getProperty("spring.mail.username");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromMailAccount);
            mimeMessageHelper.setTo(toMailAccounts);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("E-mail failed to send");
            throw e;
        }
    }

}
