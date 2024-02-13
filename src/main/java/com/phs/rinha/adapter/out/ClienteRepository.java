package com.phs.rinha.adapter.out;

import com.phs.rinha.model.Cliente;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface ClienteRepository extends R2dbcRepository<Cliente, Integer> {
    @Query(value = """
            UPDATE clientes 
            SET saldo = (saldo + :valor) 
            WHERE id = :cliente_id 
            AND (saldo + :valor) > (-limite)
            RETURNING *""")
    Mono<Cliente> updateSaldoReturningCliente(@Param("vslor") long valor, @Param("cliente_id") int clienteId);
}
