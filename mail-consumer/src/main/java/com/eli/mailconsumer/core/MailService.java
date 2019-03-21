package com.eli.mailconsumer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * 邮件发送
 * @author Eli
 */
@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private  JavaMailSender javaMailSender;


    /**
     * 发送文本邮件
     * @param to 接受对象
     * @param subject 主题
     * @param content 内容
     */
    public void sendSimpleMail(String to,String subject,String content,String from){
         SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
         simpleMailMessage.setTo(to);
         simpleMailMessage.setSubject(subject);
         simpleMailMessage.setText(content);
         simpleMailMessage.setFrom(from);

         javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送html文件
     * @param to 接受对象
     * @param subject 主题
     * @param content 内容
     * @throws MessagingException
     */
    public boolean sendHtmlMail(String to ,String subject,String content,String from) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            helper.setFrom(from);
            javaMailSender.send(message);
            logger.info("html邮件发送成功!");
            return true;
        } catch (MessagingException e) {
            logger.error("发送html邮件失败:",e);
            return false;
        }

    }


    public void sendAttachmentMail(String to ,String subject,String content,
                                   String filePath,String from) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        helper.setFrom(from);

        FileSystemResource fileSystemResource= new FileSystemResource(new File(filePath));
        String fileName=fileSystemResource.getFilename();
        helper.addAttachment(fileName,fileSystemResource);
        javaMailSender.send(message);
    }


    /**
     * 发送带图片的HTML邮件
     * @param to 接受对象
     * @param subject 主题
     * @param content 内容
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendInlineResourceMail(String to ,String subject,String content,String rscPath,String rscId,String from)
                                         throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        helper.setFrom(from);

        FileSystemResource fileSystemResource= new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,fileSystemResource);
        javaMailSender.send(message);
    }
}
