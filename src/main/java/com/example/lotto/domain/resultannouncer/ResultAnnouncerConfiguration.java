package com.example.lotto.domain.resultannouncer;

import com.example.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

public class ResultAnnouncerConfiguration {

    ResultAnnouncerFacade createForTest(ResultCheckerFacade resultCheckerFacade, ResponseRepository responseRepository, Clock clock){
        return new ResultAnnouncerFacade(resultCheckerFacade, responseRepository, clock);
    }
}
