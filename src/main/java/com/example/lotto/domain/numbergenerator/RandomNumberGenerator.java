package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.OneRandomNumberResponseDto;
import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator implements RandomNumberGenerable {

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 99;

    private final OneNumberFetcher client;

    RandomNumberGenerator(OneNumberFetcher client) {
        this.client = client;
    }
    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();

        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){
            OneRandomNumberResponseDto randomNumberResponseDto = client.retrieveOneRandomNumber(LOWER_BOUND, UPPER_BOUND);
            int randomNumber = randomNumberResponseDto.number();
            winningNumbers.add(randomNumber);
        }

        return SixRandomNumbersDto.builder()
                .numbers(winningNumbers)
                .build();
    }

    private boolean isQuantityOfNumbersLowerThanSix(Set<Integer> winningNumbers){
        return winningNumbers.size() < 6;
    }

}
