package com.ishizuka.demo.infrastructure.adapters.output;

import com.ishizuka.demo.application.ports.output.PublishMessageUseCase;
import com.ishizuka.demo.domain.exception.FailedToSendMessageToQueueException;
import com.ishizuka.demo.infrastructure.adapters.configuration.properties.SqsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsPublisher implements PublishMessageUseCase {

    private final SqsAsyncClient sqsAsyncClient;
    private final SqsProperties properties;

    @Override
    public Mono<Void> publishMessage(String message) {

        // Configura e envia a mensagem
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                .queueUrl(properties.getQueueUrl())
                .messageBody(message)
                .build();

        return Mono.fromFuture(sqsAsyncClient.sendMessage(messageRequest))
                .map(SendMessageResponse::messageId)
                .doOnError(e -> {
                    log.error("Error sending message to SQS", e);
                    throw new FailedToSendMessageToQueueException("Error sending message to SQS");
                })
                .then();
    }

}
