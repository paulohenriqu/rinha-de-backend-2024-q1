package com.phs.rinha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.ZonedDateTime;

@Table("transacoes")
public record Transacao(
        @Id
        Integer id,
        long valor,
        String descricao,
        char tipo,
        int cliente_id,
        ZonedDateTime realizada_em
) {}
