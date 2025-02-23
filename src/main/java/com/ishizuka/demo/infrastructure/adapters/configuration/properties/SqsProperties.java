package com.ishizuka.demo.infrastructure.adapters.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "sqs")
public class SqsProperties {

    String host;
    String queueUrl;

}
