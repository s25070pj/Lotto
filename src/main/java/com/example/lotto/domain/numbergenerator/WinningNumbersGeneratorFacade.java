package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {
    private final RandomNumberGenerable randomNumberGenerator;
    private final WinningNumberValidator winningNumberValidator;
    private final WinningNumbersRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumbersGeneratorFacadeConfigurationProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = numberReceiverFacade.retrieveNextDrawDate();
        SixRandomNumbersDto winningNumbersDto = randomNumberGenerator.generateSixRandomNumbers(properties.count(), properties.lowerBound(), properties.upperBound());
        Set<Integer> winningNumbers = winningNumbersDto.numbers();
        winningNumberValidator.validate(winningNumbers);
        winningNumbersRepository.save(WinningNumbers.builder()
                .winningNumbers(winningNumbers)
                .date(nextDrawDate)
                .build());

        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers)
                .build();
    }

    public WinningNumbersDto retrieveWinningNumberByDate(LocalDateTime date) {
        WinningNumbers winningNumbersFromDatabase = winningNumbersRepository.findNumbersByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Numbers not found"));

        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbersFromDatabase.winningNumbers())
                .date(winningNumbersFromDatabase.date())
                .build();
    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime nextDrawTime = numberReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawTime);
    }
}
