package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.OneRandomNumberResponseDto;

import java.security.SecureRandom;
import java.util.Random;

public class SecureOneNumberFetcher implements OneNumberFetcher{

    @Override
    public OneRandomNumberResponseDto retrieveOneRandomNumber(int lowerBound, int upperBound) {
        Random random = new SecureRandom();
        return OneRandomNumberResponseDto.builder()
                .number(random.nextInt((upperBound - lowerBound) + 1))
                .build();
    }
}
