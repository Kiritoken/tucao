package com.eli.emailserver.service.Impl;

import com.eli.emailserver.dao.TucaoMapper;
import com.eli.emailserver.entity.Tucao;
import com.eli.emailserver.service.TucaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TucaoServiceImpl implements TucaoService {
    @Autowired
    private TucaoMapper tucaoMapper;

    @Override
    public Boolean createTucao(Tucao tucao) {
        return (tucaoMapper.insert(tucao)!= 0);
    }

    @Override
    public List<Tucao> getTucaoList() {
        return tucaoMapper.getTucaoList();
    }

    @Override
    public List<Tucao> getTucaoListDescByTime() {
        return tucaoMapper.getTucaoListDescByTime();
    }
}
