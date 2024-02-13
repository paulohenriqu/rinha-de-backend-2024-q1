package com.phs.rinha.adapter.out;

import com.phs.rinha.model.Cliente;
import com.phs.rinha.model.Transacao;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface TransacaoRepository  extends R2dbcRepository<Transacao, Integer> {
    @Query(value = """
            SELECT * FROM transacoes 
            WHERE cliente_id = :cliente_id 
            ORDER BY realizada_em DESC
            LIMIT :limit
            """)
    Flux<Transacao> finLastTransactions( @Param("cliente_id") int clienteId, @Param("limit") int limit);
}
