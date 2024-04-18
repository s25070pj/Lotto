package com.example.lotto.infrastructure.numbergenerator.http;


import com.example.lotto.domain.numbergenerator.RandomNumberGenerable;
import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@AllArgsConstructor
public class RandomNumberGeneratorRestClientConfig {

    private final RandomNumberGeneratorRestClientConfigurationProperties properties;

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    RandomNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate) {
        return new RandomNumberGeneratorRestClient(restTemplate, properties.uri(), properties.port());
    }

}
