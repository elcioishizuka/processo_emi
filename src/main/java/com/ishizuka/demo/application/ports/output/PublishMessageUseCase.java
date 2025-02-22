package com.ishizuka.demo.application.ports.output;

import reactor.core.publisher.Mono;

public interface PublishMessageUseCase {

    Mono<Void> publishMessage(String message);

}
