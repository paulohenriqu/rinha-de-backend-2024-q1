package com.phs.rinha.adapter.in.dto;

import com.phs.rinha.model.Cliente;
import com.phs.rinha.model.Transacao;

import java.util.List;

public record ExtratoServiceResponse(
        Cliente cliente,
        List<Transacao> transacoes,
        Result result

) {
    public enum Result{
        NOT_FOUND,
        SUCCESS
    }
}
