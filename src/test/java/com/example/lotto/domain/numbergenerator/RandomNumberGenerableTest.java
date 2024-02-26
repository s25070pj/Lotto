package com.example.lotto.domain.numbergenerator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomNumberGenerableTest implements  RandomNumberGenerable{

    private final Set<Integer> generatedNumbers;

    public RandomNumberGenerableTest() {
        generatedNumbers = Set.of(1,2,3,4,5,6);
    }

    @Override
    public Set<Integer> generateSixRandomNumbers() {
        return generatedNumbers;
    }
}