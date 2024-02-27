package com.example.lotto.domain.numbergenerator;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomNumberGenerator implements RandomNumberGenerable {

    private final int LOWER_BOUND = 1;
    private final int UPPER_BOUND = 99;
    private final int RANDOM_NUMBER_BOUND = (UPPER_BOUND - LOWER_BOUND) + 1;
    @Override
    public Set<Integer> generateSixRandomNumbers() {
        Set<Integer> winningNumbers = new HashSet<>();

        while (isQuantityOfNumbersLowerThanSix(winningNumbers)){
            winningNumbers.add(generateRandomNumber());
        }

        return winningNumbers;
    }

    private boolean isQuantityOfNumbersLowerThanSix(Set<Integer> winningNumbers){
        return winningNumbers.size() < 6;
    }

    private int generateRandomNumber(){
        Random random = new SecureRandom();
        return random.nextInt(RANDOM_NUMBER_BOUND) +1;
    }
}
