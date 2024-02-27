package com.example.lotto.domain.numbergenerator;

import java.util.Set;

public class WinningNumberValidator {
    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 99;

    public Set<Integer> validate(Set<Integer> winningNumbers){

        if (outOfRange(winningNumbers)){
            throw new NumberOutOfRangeException("Number is out of range!");
        }
        return winningNumbers;
    }

    private boolean outOfRange(Set<Integer> winningNumbers){
        return winningNumbers.stream()
                .anyMatch(number -> number < LOWER_BOUND || number > UPPER_BOUND);
    }
}
