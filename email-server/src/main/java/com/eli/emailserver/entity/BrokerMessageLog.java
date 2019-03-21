package com.eli.emailserver.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * broker_message_log
 * @author 
 */
public class BrokerMessageLog implements Serializable {
    /**
     * 消息唯一ID
     */
    private String messageId;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 重试次数
     */
    private Integer tryCount;

    /**
     * 消息投递状态 0投递中，1投递成功，2投递失败
     */
    private String status;

    /**
     * 下一次重试时间
     */
    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        BrokerMessageLog other = (BrokerMessageLog) that;
        return (this.getMessageId() == null ? other.getMessageId() == null : this.getMessageId().equals(other.getMessageId()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getTryCount() == null ? other.getTryCount() == null : this.getTryCount().equals(other.getTryCount()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getNextRetry() == null ? other.getNextRetry() == null : this.getNextRetry().equals(other.getNextRetry()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMessageId() == null) ? 0 : getMessageId().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getTryCount() == null) ? 0 : getTryCount().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getNextRetry() == null) ? 0 : getNextRetry().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", messageId=").append(messageId);
        sb.append(", message=").append(message);
        sb.append(", tryCount=").append(tryCount);
        sb.append(", status=").append(status);
        sb.append(", nextRetry=").append(nextRetry);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}