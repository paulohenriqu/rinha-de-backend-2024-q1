package com.phs.rinha.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public record ExtratoResponse(
        Saldo saldo,
        List<Transacao> ultimas_transacoes
) {

    public record Saldo(
            long total,
            long limite,
            @JsonProperty("data_extrato")
            ZonedDateTime dataExtrato
    ){}

    public record Transacao(
            long valor,
            TipoTransacao tipo,
            String descricao,
            @JsonProperty("realizada_em")
            ZonedDateTime realizadaEm

    ){}
}
