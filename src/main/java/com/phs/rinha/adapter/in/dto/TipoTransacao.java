package com.phs.rinha.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoTransacao {
    @JsonProperty("c")
    C,
    @JsonProperty("d")
    D
}
