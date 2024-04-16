package com.example.lotto.infrastructure.numbergenerator.http;


import com.example.lotto.domain.numbergenerator.RandomNumberGenerable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RandomNumberGeneratorRestClientConfig {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler(){
        return new RestTemplateResponseErrorHandler();
    }


    @Bean
    RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler){
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(5000))
                .setReadTimeout(Duration.ofMillis(5000))
                .build();
    }

    @Bean
    RandomNumberGenerable remoteNumberGeneratorClient(RestTemplate restTemplate,
                                                      @Value("${lotto.number-generator.http.client.config.uri}") String uri,
                                                      @Value("${lotto.number-generator.http.client.config.port}") int port) {
        return new RandomNumberGeneratorRestClient(restTemplate, uri, port);
    }

}
