package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;

public class NumberGeneratorConfiguration {
    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable numberGenerator,
                                                WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade) {
        WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
        return new WinningNumbersGeneratorFacade(numberGenerator, winningNumberValidator, winningNumbersRepository, numberReceiverFacade);
    }

}
