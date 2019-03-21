package com.eli.mailconsumer.core;

import com.alibaba.fastjson.JSON;
import com.eli.mailconsumer.entity.MailMessage;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * 处理邮件
 * @author Eli
 */
@Component
public class MailReceiver {

   private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

   @Autowired
   private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "register-email-queue")
    @RabbitHandler
    public void onMailMessage(@Payload String message ,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        //解析json字符串
        MailMessage mailMessage = JSON.parseObject(message,MailMessage.class);
        logger.info("准备发送邮件:"+mailMessage.toString());

        //发送邮件
        Context context= new Context();
        Map<String,Object> map = JSON.parseObject(mailMessage.getMessage());
        context.setVariables(map);
        String emailContent = templateEngine.process("regTemplate",context);
        if(mailService.sendHtmlMail(mailMessage.getTo(),mailMessage.getSubject(),emailContent,mailMessage.getFrom())){
            //确认
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag,false);
            logger.info("邮件发送成功!");
        }else{
            logger.info("邮件发送失败!");
        }
    }
}
