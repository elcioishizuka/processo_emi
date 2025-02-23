package com.ishizuka.demo.infrastructure.adapters.output;

import com.ishizuka.demo.application.ports.output.DebitStatusOutputPort;
import com.ishizuka.demo.domain.model.DebitStatusChange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DebitStatusPersistenceAdapter implements DebitStatusOutputPort {

    private final DynamoDbAsyncTable<DebitStatusChange> debitStatusChangeTable;

    @Override
    public Mono<Void> saveStatus(DebitStatusChange debitStatusChange) {
        return Mono.fromFuture(debitStatusChangeTable.putItem(debitStatusChange))
                .doOnError(e -> log.error("Error occurred during DynamoDB writing", e));
    }

    @Override
    public Mono<Boolean> checkIfExists(DebitStatusChange debitStatusChange) {
        return Mono.fromFuture(debitStatusChangeTable.getItem(debitStatusChange))
                .hasElement()
                .doOnError(e -> log.error("Error occured during DynamoDB reading", e));
    }

    @Override
    public Mono<DebitStatusChange> findByCustomerIdAndDebitId(DebitStatusChange debitStatusChange) {
        return Mono.fromFuture(debitStatusChangeTable.getItem(debitStatusChange));
    }
}
