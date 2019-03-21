package com.eli.emailserver.task;

import com.alibaba.fastjson.JSON;
import com.eli.emailserver.constant.MailConstant;
import com.eli.emailserver.dao.BrokerMessageLogMapper;
import com.eli.emailserver.entity.BrokerMessageLog;
import com.eli.emailserver.entity.MailMessage;
import com.eli.emailserver.service.Impl.MailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * 定时任务确保邮件投递到MQ的正确性
 * @author Eli
 */
@Component
public class RetryMessage {
   private static final Logger logger = LoggerFactory.getLogger(RetryMessage.class);

   @Autowired
   private MailServiceImpl mailService;

   @Autowired
   private BrokerMessageLogMapper brokerMessageLogMapper;


    @Scheduled(initialDelay = 5000, fixedDelay = 20000)
    public void reSend(){
        //抽取消息状态为0且已经超时的消息集合
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(messageLog -> {
            //投递三次以上的消息
            if(messageLog.getTryCount() >= MailConstant.MAX_RETRY_COUNT){
                //更新失败的消息
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageLog.getMessageId(), MailConstant.SENDING_FAIL, new Date());
            } else {
                // 重试投递消息，将重试次数递增
                brokerMessageLogMapper.update4ReSend(messageLog.getMessageId(),  new Date());

                MailMessage mailMessage = JSON.parseObject(messageLog.getMessage(),MailMessage.class);
                try {
                    logger.info("-----------重新投递:"+ messageLog.getMessage());
                    mailService.resendMail2MQ(mailMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("-----------异常处理-----------");
                }
            }
        });
    }


}
