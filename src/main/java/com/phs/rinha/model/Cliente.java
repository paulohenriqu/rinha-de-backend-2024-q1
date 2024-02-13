package com.phs.rinha.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

@Table("clientes")
public record Cliente(
        @Id
        int id,
        long limite,
        long saldo
) {
}
