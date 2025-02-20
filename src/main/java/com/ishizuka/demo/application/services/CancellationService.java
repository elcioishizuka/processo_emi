package com.ishizuka.demo.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishizuka.demo.application.ports.input.CancellationUseCase;
import com.ishizuka.demo.application.ports.output.PublishMessageUseCase;
import com.ishizuka.demo.domain.model.DebitStatusChangeResponse;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancellationService implements CancellationUseCase {

    private final PublishMessageUseCase publishMessageUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ResponseWrapper<DebitStatusChangeResponse>> cancelAutoDebit(String customerId, String automaticDebitId) {
        // Se tiver uma regra de negócio, implementar aqui
        DebitStatusChangeResponse debitStatusChangeResponse;

        try {
            debitStatusChangeResponse = DebitStatusChangeResponse.builder()
                    .customerId(customerId)
                    .automaticDebitId(automaticDebitId)
                    .status("Cancelled")
                    .protocol(UUID.randomUUID().toString())
                    .build();
            String message = objectMapper.writeValueAsString(debitStatusChangeResponse);
            publishMessageUseCase.publishMessge(message);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error during object serialization", e);
        }

        return Mono.just(new ResponseWrapper<>(debitStatusChangeResponse));


    }

}
