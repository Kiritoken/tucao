package com.eli.emailserver.dao;

import com.eli.emailserver.entity.Tucao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TucaoMapper继承基类
 */
@Repository
public interface TucaoMapper extends MyBatisBaseDao<Tucao, Integer> {
    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Tucao record);

    @Override
    int insertSelective(Tucao record);

    @Override
    Tucao selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Tucao record);

    @Override
    int updateByPrimaryKey(Tucao record);

    List<Tucao> getTucaoList();

    List<Tucao> getTucaoListDescByTime();
}