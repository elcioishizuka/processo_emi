package com.ishizuka.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class DebitStatusChange {

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("automaticDebitId")
    private String automaticDebitId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("date")
    @JsonIgnore
    private String date;

    @DynamoDbPartitionKey
    public String getCustomerId() {
        return customerId;
    }

    @DynamoDbSortKey
    public String getAutomaticDebitId() {
        return automaticDebitId;
    }

    public String getStatus() {
        return status;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDate() {
        return date;
    }
}
