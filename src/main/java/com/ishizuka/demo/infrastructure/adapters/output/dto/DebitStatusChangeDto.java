package com.ishizuka.demo.infrastructure.adapters.output.dto;

import java.time.LocalDateTime;

public record DebitStatusChangeDto(String customerId, String automaticDebitId, String status, String protocol,
                                   String date) {
}
