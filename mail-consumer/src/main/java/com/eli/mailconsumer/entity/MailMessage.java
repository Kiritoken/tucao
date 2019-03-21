package com.eli.mailconsumer.entity;

import java.io.Serializable;

/**
 * mail_message
 * @author 
 */
public class MailMessage implements Serializable {


    private static final long serialVersionUID = 8355694838002464257L;
    private Integer id;

    /**
     * 关联的用户名
     */
    private String username;

    /**
     * 收件人地址
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件发送方
     */
    private String from;

    /**
     * 消息参数
     */
    private String message;

    /**
     * 邮件模板名
     */
    private String templateName;

    /**
     * 消息唯一ID
     */
    private String messageId;

    private String routingKey;

    public MailMessage(){

    }

    public MailMessage(String username, String to, String subject, String from, String message, String templateName, String messageId, String routingKey) {
        this.username = username;
        this.to = to;
        this.subject = subject;
        this.from = from;
        this.message = message;
        this.templateName = templateName;
        this.messageId = messageId;
        this.routingKey = routingKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MailMessage other = (MailMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getTo() == null ? other.getTo() == null : this.getTo().equals(other.getTo()))
            && (this.getSubject() == null ? other.getSubject() == null : this.getSubject().equals(other.getSubject()))
            && (this.getFrom() == null ? other.getFrom() == null : this.getFrom().equals(other.getFrom()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getTemplateName() == null ? other.getTemplateName() == null : this.getTemplateName().equals(other.getTemplateName()))
            && (this.getMessageId() == null ? other.getMessageId() == null : this.getMessageId().equals(other.getMessageId()))
            && (this.getRoutingKey() == null ? other.getRoutingKey() == null : this.getRoutingKey().equals(other.getRoutingKey()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getTo() == null) ? 0 : getTo().hashCode());
        result = prime * result + ((getSubject() == null) ? 0 : getSubject().hashCode());
        result = prime * result + ((getFrom() == null) ? 0 : getFrom().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getTemplateName() == null) ? 0 : getTemplateName().hashCode());
        result = prime * result + ((getMessageId() == null) ? 0 : getMessageId().hashCode());
        result = prime * result + ((getRoutingKey() == null) ? 0 : getRoutingKey().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", to=").append(to);
        sb.append(", subject=").append(subject);
        sb.append(", from=").append(from);
        sb.append(", message=").append(message);
        sb.append(", templateName=").append(templateName);
        sb.append(", messageId=").append(messageId);
        sb.append(", routingKey=").append(routingKey);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}