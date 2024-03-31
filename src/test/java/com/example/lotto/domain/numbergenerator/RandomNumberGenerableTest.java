package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomNumberGenerableTest implements  RandomNumberGenerable{

    private final Set<Integer> generatedNumbers;

    public RandomNumberGenerableTest() {
        generatedNumbers = Set.of(1,2,3,4,5,6);
    }

    public RandomNumberGenerableTest(Set<Integer> generatedNumbers){
        this.generatedNumbers = generatedNumbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}