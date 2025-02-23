package com.ishizuka.demo.infrastructure.adapters.configuration;

import com.ishizuka.demo.domain.model.DebitStatusChange;
import com.ishizuka.demo.infrastructure.adapters.configuration.properties.DynamoDbProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryMode;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.crt.AwsCrtAsyncHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DynamoDbConfig {

    private final DynamoDbProperties properties;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient(){

        // Ajustes a serem realizados conforme resultados do teste de carga
        AwsCrtAsyncHttpClient.Builder awsCrtAsyncHttpClient = AwsCrtAsyncHttpClient.builder()
                .maxConcurrency(1000)
                .connectionTimeout(Duration.ofSeconds(30))
                .connectionMaxIdleTime(Duration.ofSeconds(60));

        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(properties.getHost()))
                .httpClientBuilder(awsCrtAsyncHttpClient)
                .overrideConfiguration(ClientOverrideConfiguration.builder()
                        .retryStrategy(RetryMode.ADAPTIVE_V2)
                        .build())
                .build();
    }

    @Bean
    public DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient() {
        return DynamoDbEnhancedAsyncClient.builder()
                .dynamoDbClient(dynamoDbAsyncClient())
                .build();
    }

    @Bean
    public DynamoDbAsyncTable<DebitStatusChange> debitStatusChangeTable(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient) {
        return dynamoDbEnhancedAsyncClient.table("DebitStatusChange", TableSchema.fromBean(DebitStatusChange.class));
    }
}
