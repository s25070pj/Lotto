package com.example.lotto;

import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import com.example.lotto.infrastructure.numbergenerator.http.RandomNumberGeneratorRestClientConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        WinningNumbersGeneratorFacadeConfigurationProperties.class,
        RandomNumberGeneratorRestClientConfigurationProperties.class
})
public class LottoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoApplication.class, args);
    }

}
