package com.ishizuka.demo.infrastructure.adapters.output;

import com.ishizuka.demo.application.ports.output.PublishMessageUseCase;
import com.ishizuka.demo.domain.exception.FailedToSendMessageToQueueException;
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

    private final String QUEUE_URL = "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/debit-queue";

    @Override
    public void publishMessge(String message) {

        // Configura e envia a mensagem
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                .queueUrl(QUEUE_URL)
                .messageBody(message)
                .build();

        Mono.fromFuture(sqsAsyncClient.sendMessage(messageRequest))
                .map(SendMessageResponse::messageId)
                .doOnError(e -> {
                    throw new FailedToSendMessageToQueueException("Error sending message to SQS");
                });
    }

}
