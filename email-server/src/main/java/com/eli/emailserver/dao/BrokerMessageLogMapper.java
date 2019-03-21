package com.eli.emailserver.dao;

import com.eli.emailserver.entity.BrokerMessageLog;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * BrokerMessageLogMapper继承基类
 */
@Repository
public interface BrokerMessageLogMapper extends MyBatisBaseDao<BrokerMessageLog, String> {
    @Override
    int deleteByPrimaryKey(String id);

    @Override
    int insert(BrokerMessageLog record);

    @Override
    int insertSelective(BrokerMessageLog record);

    @Override
    BrokerMessageLog selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(BrokerMessageLog record);

    @Override
    int updateByPrimaryKey(BrokerMessageLog record);

    /**
     * 更新状态
     * @param messageId ID
     * @param status 状态
     * @param updateTime 更新时间
     */
    void changeBrokerMessageLogStatus(String messageId, String status, Date updateTime);

    List<BrokerMessageLog> query4StatusAndTimeoutMessage();

    void update4ReSend(String messageId, Date updateTime);
}