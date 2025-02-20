package com.ishizuka.demo.application.ports.input;

import com.ishizuka.demo.domain.model.DebitStatusChangeResponse;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import reactor.core.publisher.Mono;

public interface CancellationUseCase {
    Mono<ResponseWrapper<DebitStatusChangeResponse>> cancelAutoDebit(String customerId, String automaticDebitId);
}
