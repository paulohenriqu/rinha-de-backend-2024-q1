package com.phs.rinha.adapter.in;

import com.phs.rinha.adapter.in.dto.*;
import com.phs.rinha.adapter.out.TransacaoRepository;
import com.phs.rinha.service.SaldoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private SaldoService saldoService;
    @Autowired
    private TransacaoRepository transacaoRepository;


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

    @GetMapping("/{id}/extrato")
    public Mono<ExtratoResponse> getExtrato(@PathVariable int id){

       return saldoService.getTransactions(id)
               .flatMap(extratoServiceResponse -> {
                   if(extratoServiceResponse.result() == ExtratoServiceResponse.Result.NOT_FOUND){
                       return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found"));
                   }
                   var cliente = extratoServiceResponse.cliente();

                   var transacoesDTO = extratoServiceResponse.transacoes().stream()
                           .map(tx -> {
                               var tipo = TipoTransacao.valueOf(String.valueOf(tx.tipo()).toUpperCase());
                               return new ExtratoResponse.Transacao(tx.valor(), tipo, tx.descricao(), tx.realizada_em().atZoneSameInstant(ZoneOffset.UTC));
                           })
                           .toList();

                   return Mono.just(
                           new ExtratoResponse(
                                new ExtratoResponse.Saldo(cliente.saldo(), cliente.limite(), ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC)),
                                   transacoesDTO
                           )

                   );
               });
    }
}
