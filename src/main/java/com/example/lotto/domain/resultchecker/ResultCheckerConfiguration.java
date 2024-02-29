package com.example.lotto.domain.resultchecker;

import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;

public class ResultCheckerConfiguration {

    ResultCheckerFacade creteForTest(WinningNumbersGeneratorFacade winningNumbersGeneratorFacade,
                                     NumberReceiverFacade numberReceiverFacade,
                                     PlayerRepository playerRepository) {
        WinnersRetriever winnerGenerator = new WinnersRetriever();
        return new ResultCheckerFacade(winningNumbersGeneratorFacade, numberReceiverFacade, playerRepository, winnerGenerator);
    }
}
