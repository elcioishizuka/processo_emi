package com.ishizuka.demo.application.ports.input;

import com.ishizuka.demo.domain.model.ResponseWrapper;
import com.ishizuka.demo.infrastructure.adapters.output.dto.DebitStatusChangeDto;
import reactor.core.publisher.Mono;

public interface CancellationUseCase {
    Mono<ResponseWrapper<DebitStatusChangeDto>> cancelAutoDebit(String customerId, String automaticDebitId);
}
