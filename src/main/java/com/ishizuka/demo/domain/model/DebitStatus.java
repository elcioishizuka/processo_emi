package com.ishizuka.demo.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DebitStatus {
    // Outros status podem ser adicionados conforme requisito do projeto
    CANCELLED("CANCELLED"),
    FAILED("FAILED");

    @Getter
    private final String description;

}
