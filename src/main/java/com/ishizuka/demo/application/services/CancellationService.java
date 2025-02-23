package com.ishizuka.demo.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishizuka.demo.application.ports.input.CancellationUseCase;
import com.ishizuka.demo.application.ports.output.DebitStatusOutputPort;
import com.ishizuka.demo.application.ports.output.PublishMessageUseCase;
import com.ishizuka.demo.domain.exception.DebitCancelAlreadyInQueueOrProcessedException;
import com.ishizuka.demo.domain.model.DebitStatus;
import com.ishizuka.demo.domain.model.DebitStatusChange;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import com.ishizuka.demo.infrastructure.adapters.output.dto.DebitStatusChangeDto;
import com.ishizuka.demo.infrastructure.adapters.output.mapper.DebitStatusChangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CancellationService implements CancellationUseCase {

    private final PublishMessageUseCase publishMessageUseCase;
    private final DebitStatusOutputPort debitStatusOutputPort;
    private final ObjectMapper objectMapper;
    private final DebitStatusChangeMapper debitStatusChangeMapper;

    public Mono<ResponseWrapper<DebitStatusChangeDto>> cancelAutoDebit(String customerId, String automaticDebitId) {
        // Se tiver uma regra de negócio adicional, implementar aqui

        DebitStatusChange debitStatusChange = DebitStatusChange.builder()
                .customerId(customerId)
                .automaticDebitId(automaticDebitId)
                .build();

        log.info("Starting cancellation for customer {} and debit {}", customerId, automaticDebitId);

        return debitStatusOutputPort.checkIfExists(debitStatusChange)
                .flatMap(exists -> {
                    if (exists) {
                        // Retorna erro se já existir o Id do débito se já estiver registro no banco de dados
                        log.error("Automatic Debit ID {} was already cancelled or it is in progress", automaticDebitId);
                        return Mono.error(new DebitCancelAlreadyInQueueOrProcessedException(
                                String.format("Automatic Debit ID %s was already cancelled or it is in progress", automaticDebitId)));
                    }

                    // Atualiza os dados do status
                    debitStatusChange.setStatus(DebitStatus.CANCELLED.name());
                    debitStatusChange.setProtocol(UUID.randomUUID().toString());
                    debitStatusChange.setDate(LocalDateTime.now().toString());

                    DebitStatusChangeDto debitStatusChangeDto = debitStatusChangeMapper.toDebitStatusChangeDto(debitStatusChange);

                    // Serializa o objeto para JSON para ser publicado na fila
                    return Mono.fromCallable(() -> objectMapper.writeValueAsString(debitStatusChangeDto))
                            .flatMap(message -> {
                                // Publica a mensagem na fila e salva no banco de dados
                                return publishMessageUseCase.publishMessage(message)
                                        .then(debitStatusOutputPort.saveStatus(debitStatusChange))
                                        .thenReturn(new ResponseWrapper<>(debitStatusChangeDto));
                            });
                })
                .onErrorMap(JsonProcessingException.class, e ->
                        new IllegalArgumentException("Error during object serialization", e)
                )
                .onErrorResume(Mono::error)
                .doOnTerminate(() -> log.info("Request finished"));
    }
}
