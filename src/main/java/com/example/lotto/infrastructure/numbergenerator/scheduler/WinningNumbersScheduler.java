package com.example.lotto.infrastructure.numbergenerator.scheduler;


import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class WinningNumbersScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;


    @Scheduled(cron = "${lotto.number-generator.lotteryRunOccurrence}")
    public WinningNumbersDto generateWinningNumbers(){
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        log.info(winningNumbersDto.getWinningNumbers());
        log.info(winningNumbersDto.getDate());

        return winningNumbersDto;
    }
}
