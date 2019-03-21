package com.eli.emailserver.service;

import com.eli.emailserver.entity.BrokerMessageLog;
import com.eli.emailserver.entity.MailMessage;

/**
 * 邮件服务
 * @author Eli
 */
public interface MailService {
   void sendMail2MQ(MailMessage mailMessage);

   void resendMail2MQ(MailMessage mailMessage);
}
