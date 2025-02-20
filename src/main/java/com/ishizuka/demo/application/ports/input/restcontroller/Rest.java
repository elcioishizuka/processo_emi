package com.ishizuka.demo.application.ports.input.restcontroller;


import com.ishizuka.demo.domain.model.DebitStatusChangeResponse;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

public interface Rest {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/customers/{customerId}/debits/{automaticDebitId}/cancel")
    Mono<ResponseWrapper<DebitStatusChangeResponse>> cancelAutoDebit(@PathVariable String customerId,
                                                                     @PathVariable String automaticDebitId);

}
