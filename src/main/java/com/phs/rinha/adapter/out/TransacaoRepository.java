package com.phs.rinha.adapter.out;

import com.phs.rinha.model.Cliente;
import com.phs.rinha.model.Transacao;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TransacaoRepository  extends R2dbcRepository<Transacao, Integer> {
}
