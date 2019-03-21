package com.eli.emailserver.dao;

import com.eli.emailserver.entity.MailMessage;
import org.springframework.stereotype.Repository;

/**
 * MailMessageMapper继承基类
 */
@Repository
public interface MailMessageMapper extends MyBatisBaseDao<MailMessage, Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(MailMessage record);

    @Override
    int insertSelective(MailMessage record);

    @Override
    MailMessage selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(MailMessage record);

    @Override
    int updateByPrimaryKey(MailMessage record);
}