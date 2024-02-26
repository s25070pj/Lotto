package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WinningNumbersGeneratorFacadeTest {

    private final WinningNumbersRepository winningNumbersRepository = new WinningNumbersRepositoryTest();
    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);

    @Test
    public void it_should_return_set_of_required_size() {
        //given
        RandomNumberGenerable generator = new RandomNumberGenerator();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator,winningNumbersRepository,numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers  = numbersGenerator.generateWinningNumbers();
        //then

        assertThat(generatedNumbers.getWinningNumbers().size()).isEqualTo(6);
    }



}

