package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.OneRandomNumberResponseDto;

public interface OneNumberFetcher {
    OneRandomNumberResponseDto retrieveOneRandomNumber(int lowerBound, int upperBound);
}