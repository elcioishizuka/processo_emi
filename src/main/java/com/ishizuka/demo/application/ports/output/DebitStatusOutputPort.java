package com.ishizuka.demo.application.ports.output;

import com.ishizuka.demo.domain.model.DebitStatusChange;
import reactor.core.publisher.Mono;

public interface DebitStatusOutputPort {

    Mono<Void> saveStatus(DebitStatusChange debitStatusChange);

    Mono<Boolean> checkIfExists(DebitStatusChange debitStatusChange);

    Mono<DebitStatusChange> findByCustomerIdAndDebitId(DebitStatusChange debitStatusChange);

}
