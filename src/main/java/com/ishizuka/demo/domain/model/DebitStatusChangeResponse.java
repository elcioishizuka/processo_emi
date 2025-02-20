package com.ishizuka.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitStatusChangeResponse {

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("automaticDebitId")
    private String automaticDebitId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("protocol")
    private String protocol;

}
