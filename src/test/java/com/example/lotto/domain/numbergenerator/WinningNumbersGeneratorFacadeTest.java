package com.example.lotto.domain.numbergenerator;

import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        RandomNumberGenerable generator = new SecureRandomNumberGenerator();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = numbersGenerator.generateWinningNumbers();
        //then

        assertThat(generatedNumbers.getWinningNumbers().size()).isEqualTo(6);
    }

    @Test
    public void it_should_return_set_of_required_size_within_required_range() {
        //given
        RandomNumberGenerable generator = new RandomNumberGenerableTest();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generatedNumbers = numbersGenerator.generateWinningNumbers();
        //then
        int lowerBound = 1;
        int upperBound = 99;
        Set<Integer> generatedNumbersSet = generatedNumbers.getWinningNumbers();
        boolean areNumbersInRange = generatedNumbersSet.stream()
                .allMatch(number -> number >= lowerBound && number <= upperBound);

        assertThat(areNumbersInRange).isTrue();

    }


    @Test
    public void it_should_throw_an_exception_when_number_not_in_range() {
        //given
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 120);
        RandomNumberGenerable generator = new RandomNumberGenerableTest(numbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when

        //then
        //assertThrows(NumberOutOfRangeException.class, () ->  numbersGenerator.generateWinningNumbers(), "Number is out of range!");
        assertThrows(NumberOutOfRangeException.class, numbersGenerator::generateWinningNumbers, "Number is out of range!");


    }


    @Test
    public void it_should_return_collection_of_unique_values() {
        //given
        RandomNumberGenerable generator = new RandomNumberGenerableTest();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto generated = numbersGenerator.generateWinningNumbers();
        int size = new HashSet<>(generated.getWinningNumbers()).size();
        //then
        assertThat(size).isEqualTo(6);

    }


    @Test
    public void it_should_return_winning_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 2, 25, 12, 0, 0);
        Set<Integer> generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(UUID.randomUUID().toString())
                .winningNumbers(generatedNumbers)
                .date(drawDate)
                .build();

        winningNumbersRepository.save(winningNumbers);

        RandomNumberGenerable generator = new RandomNumberGenerableTest();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = numbersGenerator.retrieveWinningNumberByDate(drawDate);
        WinningNumbersDto expectedNumbersDto = WinningNumbersDto.builder()
                .date(drawDate)
                .winningNumbers(generatedNumbers)
                .build();
        //then
        assertThat(winningNumbersDto.getWinningNumbers()).isEqualTo(expectedNumbersDto.getWinningNumbers());

    }

    @Test
    public void it_should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 2, 25, 12, 0, 0);
        RandomNumberGenerable generator = new RandomNumberGenerableTest();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, () -> numbersGenerator.retrieveWinningNumberByDate(drawDate), "Retrieving failed!");

    }

    @Test
    public void it_should_return_true_if_numbers_are_generated_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 2, 25, 12, 0, 0);
        Set<Integer> generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(UUID.randomUUID().toString())
                .winningNumbers(generatedNumbers)
                .date(drawDate)
                .build();

        winningNumbersRepository.save(winningNumbers);

        RandomNumberGenerable generator = new RandomNumberGenerableTest();

        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        WinningNumbersGeneratorFacade numbersGenerator = new NumberGeneratorConfiguration().createForTest(generator, winningNumbersRepository, numberReceiverFacade);
        //when
        WinningNumbersDto winningNumbersDto = numbersGenerator.retrieveWinningNumberByDate(drawDate);
        WinningNumbersDto expectedNumbersDto = WinningNumbersDto.builder()
                .date(drawDate)
                .winningNumbers(generatedNumbers)
                .build();
        //then
        // assertThat(numbersGenerator.areWinningNumbersGeneratedByDate()).isTrue();
        assertTrue(numbersGenerator.areWinningNumbersGeneratedByDate());
    }


}

