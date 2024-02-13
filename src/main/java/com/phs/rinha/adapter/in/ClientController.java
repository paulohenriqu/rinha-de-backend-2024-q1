package com.phs.rinha.adapter.in;

import com.phs.rinha.adapter.in.dto.SaldoResponse;
import com.phs.rinha.adapter.in.dto.TransactionRequest;
import com.phs.rinha.service.SaldoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private SaldoService saldoService;

    @PostMapping("/{id}/transacoes")
    public Mono<SaldoResponse> createTransaction(@PathVariable int id, @Valid @RequestBody TransactionRequest transacao){

        return saldoService.processTransaction(id, transacao)
                .flatMap(clienteServiceResponse -> switch (clienteServiceResponse.result()){
                    case NOT_FOUND -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found"));
                    case INSUFFICIENT_LIMIT -> Mono.error(new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "insufficient limit"));
                    case SUCCESS -> Mono.just(new SaldoResponse(clienteServiceResponse.cliente().limite(),clienteServiceResponse.cliente().saldo()));
                    default -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unexpected result"));
                 });
    }
}
