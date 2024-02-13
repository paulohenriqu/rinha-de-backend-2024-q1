package com.phs.rinha.service;

import com.phs.rinha.adapter.out.ClienteRepository;
import com.phs.rinha.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Cacheable("clientes")
    public Mono<Cliente> getClienteById(int id){
        return clienteRepository.findById(id);
    }

}
