package com.eli.emailserver.service;

import com.eli.emailserver.entity.Tucao;

import java.util.List;

public interface TucaoService {

    Boolean createTucao(Tucao tucao);

    List<Tucao> getTucaoList();

    List<Tucao> getTucaoListDescByTime();
}
