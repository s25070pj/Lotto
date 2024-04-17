package com.example.lotto.domain.numbergenerator;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@ConfigurationProperties(prefix = "lotto.number-generator.facade")

public record WinningNumbersGeneratorFacadeConfigurationProperties(int count, int lowerBound, int upperBound


) {
}
