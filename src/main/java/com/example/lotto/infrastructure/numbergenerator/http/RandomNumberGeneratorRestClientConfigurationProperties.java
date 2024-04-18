package com.example.lotto.infrastructure.numbergenerator.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.number-generator.http.client.config")
public record RandomNumberGeneratorRestClientConfigurationProperties(String uri,
                                                                     long port,
                                                                     long connectionTimeout,
                                                                     long readTimeout) {
}
