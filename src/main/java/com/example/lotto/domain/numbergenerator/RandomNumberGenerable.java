package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

public interface RandomNumberGenerable {
    SixRandomNumbersDto generateSixRandomNumbers();
}
