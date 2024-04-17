package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
public class SecureRandomNumberGenerator implements RandomNumberGenerable {

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 99;


    @Override
    public SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerBound, int upperBound) {
        Set<Integer> winningNumbers = new HashSet<>();

        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){

            Random random = new SecureRandom();
            int number = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1);
            winningNumbers.add(number);
        }

        return SixRandomNumbersDto.builder()
                .numbers(winningNumbers)
                .build();
    }

    private boolean isQuantityOfNumbersLowerThanSix(Set<Integer> winningNumbers){
        return winningNumbers.size() < 6;
    }

}
