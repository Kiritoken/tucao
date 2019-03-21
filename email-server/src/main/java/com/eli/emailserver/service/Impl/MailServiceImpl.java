package com.eli.emailserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.eli.emailserver.constant.MailConstant;
import com.eli.emailserver.dao.BrokerMessageLogMapper;
import com.eli.emailserver.dao.MailMessageMapper;
import com.eli.emailserver.entity.BrokerMessageLog;
import com.eli.emailserver.entity.MailMessage;
import com.eli.emailserver.service.MailService;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 邮件
 * @author Eli
 */
@Service
public class MailServiceImpl implements MailService {
  private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private BrokerMessageLogMapper brokerMessageLogMapper;


  @Autowired
  private MailMessageMapper mailMessageMapper;


    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            logger.info("correlationData："+correlationData);
            String messageId = correlationData.getId();
            if (ack){
                //如果返回成功，更新记录表
                brokerMessageLogMapper.changeBrokerMessageLogStatus(messageId,MailConstant.SENDING_SUCCESS,new Date());
                logger.info("投递成功!");
            }else {
                //失败进行操作：根据具体失败原因选择重试或补偿等手段
                logger.error("异常处理"+cause);
            }
        }
    };

    @Override
    public void sendMail2MQ(MailMessage mailMessage) {
        //设置回调
            rabbitTemplate.setConfirmCallback(confirmCallback);
        //数据库保存消息
        mailMessageMapper.insert(mailMessage);
        //数据库保存 消息记录
        BrokerMessageLog brokerMessageLog = generateBrokerMessageLog(mailMessage);
        brokerMessageLogMapper.insert(brokerMessageLog);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(mailMessage.getMessageId());
        rabbitTemplate.convertAndSend("email-exchange", mailMessage.getRoutingKey(),
                                           brokerMessageLog.getMessage(), correlationData);
    }

    @Override
    public void resendMail2MQ(MailMessage mailMessage) {
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(mailMessage.getMessageId());
        rabbitTemplate.convertAndSend("email-exchange", mailMessage.getRoutingKey(),
               JSON.toJSONString(mailMessage), correlationData);
    }


    private BrokerMessageLog generateBrokerMessageLog(MailMessage mailMessage){
        Date time = new Date();
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(mailMessage.getMessageId());
        brokerMessageLog.setMessage(JSON.toJSONString(mailMessage));
        brokerMessageLog.setStatus(MailConstant.SENDING);
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(time, MailConstant.TIMEOUT));
        brokerMessageLog.setCreateTime(time);
        brokerMessageLog.setTryCount(0);
        brokerMessageLog.setUpdateTime(time);
        return brokerMessageLog;
    }
}
