package com.phs.rinha.adapter.in.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record ExtratoResponse(
        Saldo saldo,
        List<Transacao> ultimas_transacoes
) {

    record Saldo(
            long total,
            long limite,
            ZonedDateTime dataExtrato
    ){}

    record Transacao(
            long valor,
            TipoTransacao tipo,
            String descricao,
            ZonedDateTime realizadaEm

    ){}
}
