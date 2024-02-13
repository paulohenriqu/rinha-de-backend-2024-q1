package com.phs.rinha.adapter.in.dto;

import jakarta.validation.constraints.*;

public record TransactionRequest(
        @Min(value = 0L, message = "O valor precisa ser positivo")
        long valor,
        @NotNull
        TipoTransacao tipo,
        @NotNull
        @Size(min = 1, max = 10)
        String descricao
) { }
