package com.phs.rinha.adapter.in.dto;

import com.phs.rinha.model.Cliente;

public record ClienteServiceResponse(
        Cliente cliente,
        Result result
) {
    public enum Result{
        NOT_FOUND,
        INSUFFICIENT_LIMIT,
        SUCCESS
    }
}
