package com.example.lotto.domain.numberreceiver;

import java.util.Set;

class NumberValidator {

    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MINIMAL_NUMBER_FROM_USER = 1;
    private static final int MAXIMUM_NUMBER_FROM_USER = 99;

    boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .filter(number -> number >= MINIMAL_NUMBER_FROM_USER)
                .filter(number -> number <= MAXIMUM_NUMBER_FROM_USER)
                .count() == MAX_NUMBERS_FROM_USER;
    }
}
