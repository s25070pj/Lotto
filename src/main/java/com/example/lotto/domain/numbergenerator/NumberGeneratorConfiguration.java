package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;


@Configuration
public class NumberGeneratorConfiguration {

    @Bean
    NumberReceiverFacade numberReceiverFacade(){
        return new NumberReceiverFacade(null, null, null, null);
    }

    @Bean
    WinningNumbersRepository winningNumbersRepository(){
        return new WinningNumbersRepository() {
            @Override
            public Optional<WinningNumbers> findNumbersByDate(LocalDateTime date) {
                return Optional.empty();
            }

            @Override
            public boolean existsByDate(LocalDateTime nextDrawDate) {
                return false;
            }

            @Override
            public WinningNumbers save(WinningNumbers winningNumbers) {
                return null;
            }
        };
    }

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(WinningNumbersRepository winningNumbersRepository, NumberReceiverFacade numberReceiverFacade, RandomNumberGenerable randomNumberGenerator, WinningNumbersGeneratorFacadeConfigurationProperties properties) {
        WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
        return new WinningNumbersGeneratorFacade(randomNumberGenerator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, properties);
    }


    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable numberGenerator,
                                                WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade) {
        WinningNumbersGeneratorFacadeConfigurationProperties properties = WinningNumbersGeneratorFacadeConfigurationProperties.builder()
                .count(6)
                .lowerBound(1)
                .upperBound(9)
                .build();
        return winningNumbersGeneratorFacade(winningNumbersRepository, numberReceiverFacade, numberGenerator, properties);
    }

}
