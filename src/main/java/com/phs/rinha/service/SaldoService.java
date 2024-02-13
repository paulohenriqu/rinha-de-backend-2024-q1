package com.phs.rinha.service;

import com.phs.rinha.adapter.in.dto.ClienteServiceResponse;
import com.phs.rinha.adapter.in.dto.TipoTransacao;
import com.phs.rinha.adapter.in.dto.TransactionRequest;
import com.phs.rinha.adapter.out.ClienteRepository;
import com.phs.rinha.adapter.out.TransacaoRepository;
import com.phs.rinha.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaldoService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    TransacaoRepository transacaoRepository;
    @Autowired
    ClienteService clienteService;

    public Mono<ClienteServiceResponse> processTransaction(int clienteId, TransactionRequest transacao){
        return clienteService.getClienteById(clienteId)
                .flatMap(cliente -> {
                  var valor = transacao.tipo() == TipoTransacao.D ? -(transacao.valor()) : transacao.valor();

                    return clienteRepository.updateSaldoReturningCliente(valor, clienteId)
                            .flatMap(updated -> transacaoRepository.save(
                                    new Transacao(
                                            null,
                                            transacao.valor(),
                                            transacao.descricao(),
                                            transacao.tipo().toString().toLowerCase().charAt(0),
                                            clienteId,
                                            null )
                            )
                                    .flatMap(tx ->  Mono.just(new ClienteServiceResponse(updated, ClienteServiceResponse.Result.SUCCESS)) ))
                            .switchIfEmpty(Mono.just(new ClienteServiceResponse(null, ClienteServiceResponse.Result.INSUFFICIENT_LIMIT)));
                })
                .switchIfEmpty(
                        Mono.just(
                        new ClienteServiceResponse(null, ClienteServiceResponse.Result.NOT_FOUND)
                        )
                );
    }
}
