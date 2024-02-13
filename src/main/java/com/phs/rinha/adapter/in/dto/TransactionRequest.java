package com.phs.rinha.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @Positive
        long valor,
        @NotNull
        TipoTransacao tipo,
        @NotBlank
        String descricao
) { }
