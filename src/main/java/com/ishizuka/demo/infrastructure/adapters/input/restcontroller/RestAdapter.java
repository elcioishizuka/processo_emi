package com.ishizuka.demo.infrastructure.adapters.input.restcontroller;

import com.ishizuka.demo.application.ports.input.restcontroller.Rest;
import com.ishizuka.demo.application.services.CancellationService;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import com.ishizuka.demo.infrastructure.adapters.output.dto.DebitStatusChangeDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/auto-debit/v1")
@AllArgsConstructor
public class RestAdapter implements Rest {

    private final CancellationService cancellationService;

    @Override
    public Mono<ResponseWrapper<DebitStatusChangeDto>> cancelAutoDebit(String customerId, String automaticDebitId) {
        return cancellationService.cancelAutoDebit(customerId, automaticDebitId);
    }
}
