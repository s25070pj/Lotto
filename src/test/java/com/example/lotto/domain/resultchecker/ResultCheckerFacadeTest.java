package com.example.lotto.domain.resultchecker;

import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.example.lotto.domain.numberreceiver.dto.TicketDto;
import com.example.lotto.domain.resultchecker.dto.PlayersDto;
import com.example.lotto.domain.resultchecker.dto.ResultDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultCheckerFacadeTest {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade = mock(WinningNumbersGeneratorFacade.class);
    private final NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
    private final PlayerRepository playerRepository = new PlayerRepositoryTest();


    @Test
    public void it_should_generate_all_players_with_correct_message() {
        //given
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();

        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .build());

        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(List.of(TicketDto.builder()
                        .hash("1")
                        .numbers(Set.of(1, 2, 3, 4, 5, 6))
                        .drawDate(drawDate)
                        .build(),
                TicketDto.builder()
                        .hash("2")
                        .numbers(Set.of(1, 22, 33, 44, 55, 6))
                        .drawDate(drawDate)
                        .build(),
                TicketDto.builder()
                        .hash("3")
                        .numbers(Set.of(1, 2, 3, 44, 55, 66))
                        .drawDate(drawDate)
                        .build()
        ));

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().creteForTest(winningNumbersGeneratorFacade, numberReceiverFacade, playerRepository);

        //when
        PlayersDto winners = resultCheckerFacade.generateWinners();
        //then
        List<ResultDto> results = winners.results();
        ResultDto resultDto = ResultDto.builder()
                .hash("1")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 5, 6))
                .drawTime(drawDate)
                .isWinner(true)
                .build();
        ResultDto resultDto1 = ResultDto.builder()
                .hash("2")
                .numbers(Set.of(1, 22, 33, 44, 55, 6))
                .hitNumbers(Set.of(1, 6))
                .drawTime(drawDate)
                .isWinner(true)
                .build();
        ResultDto resultDto2 = ResultDto.builder()
                .hash("3")
                .numbers(Set.of(1, 2, 3, 44, 55, 66))
                .hitNumbers(Set.of(1, 2, 3))
                .drawTime(drawDate)
                .isWinner(true)
                .build();

        assertThat(results).contains(resultDto, resultDto2);
        assertThat(results).doesNotContain(resultDto1);
        String message = winners.message();
        assertThat(message).isEqualTo("Winners succeeded to retrieve");

    }


    @Test
    public void it_should_generate_fail_message_when_winningNumbers_equal_null() {
        //given
        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(null)
                .build());

//        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(List.of(TicketDto.builder()
//                        .hash("1")
//                        .numbers(Set.of(1, 2, 3, 4, 5, 6))
//                        .drawDate(drawDate)
//                        .build(),
//                TicketDto.builder()
//                        .hash("2")
//                        .numbers(Set.of(1, 22, 33, 44, 55, 6))
//                        .drawDate(drawDate)
//                        .build(),
//                TicketDto.builder()
//                        .hash("3")
//                        .numbers(Set.of(1, 2, 3, 44, 55, 66))
//                        .drawDate(drawDate)
//                        .build()
//        ));

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().creteForTest(winningNumbersGeneratorFacade, numberReceiverFacade, playerRepository);

        //when
        PlayersDto winners = resultCheckerFacade.generateWinners();
        //then
//        List<ResultDto> results = winners.results();
//        ResultDto resultDto = ResultDto.builder()
//                .hash("1")
//                .numbers(Set.of(1, 2, 3, 4, 5, 6))
//                .hitNumbers(Set.of())
//                .drawTime(drawDate)
//                .isWinner(true)
//                .build();
//        ResultDto resultDto1 = ResultDto.builder()
//                .hash("2")
//                .numbers(Set.of(7, 8, 33, 44, 55, 6))
//                .hitNumbers(Set.of(7,8))
//                .drawTime(drawDate)
//                .isWinner(true)
//                .build();
//        ResultDto resultDto2 = ResultDto.builder()
//                .hash("3")
//                .numbers(Set.of(1, 2, 3, 44, 55, 66))
//                .hitNumbers(Set.of())
//                .drawTime(drawDate)
//                .isWinner(true)
//                .build();


        String message = winners.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }


    @Test
    public void it_should_generate_fail_message_when_winningNumbers_is_empty() {

        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of())
                .build());


        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().creteForTest(winningNumbersGeneratorFacade, numberReceiverFacade, playerRepository);

        //when
        PlayersDto winners = resultCheckerFacade.generateWinners();
        //then

        String message = winners.message();
        assertThat(message).isEqualTo("Winners failed to retrieve");

    }

    @Test
    public void it_should_generate_result_with_correct_credentials() {
        //given
        LocalDateTime drawDate = numberReceiverFacade.retrieveNextDrawDate();


        when(winningNumbersGeneratorFacade.generateWinningNumbers()).thenReturn(WinningNumbersDto.builder()
                .winningNumbers(Set.of(1, 2, 3, 4, 55, 7))
                .build());

        when(numberReceiverFacade.retrieveAllTicketsByNextDrawDate()).thenReturn(List.of(TicketDto.builder()
                        .hash("1")
                        .numbers(Set.of(1, 2, 3, 4, 5, 6))
                        .drawDate(drawDate)
                        .build(),
                TicketDto.builder()
                        .hash("2")
                        .numbers(Set.of(1, 22, 33, 44, 55, 6))
                        .drawDate(drawDate)
                        .build(),
                TicketDto.builder()
                        .hash("3")
                        .numbers(Set.of(1, 2, 3, 44, 55, 66))
                        .drawDate(drawDate)
                        .build()
        ));

        ResultCheckerFacade resultCheckerFacade = new ResultCheckerConfiguration().creteForTest(winningNumbersGeneratorFacade, numberReceiverFacade, playerRepository);

        //when

        ResultDto result = resultCheckerFacade.findByHash("1");
        ResultDto result2 = resultCheckerFacade.findByHash("2");
        //then
        ResultDto expectedResult = ResultDto.builder()
                .hash("1")
                .drawTime(drawDate)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4))
                .isWinner(true)
                .build();
        ResultDto expectedResult2 = ResultDto.builder()
                .hash("2")
                .drawTime(drawDate)
                .numbers(Set.of(1, 22, 33, 44, 55, 6))
                .hitNumbers(Set.of(1, 55))
                .isWinner(false)
                .build();


        assertThat(result).isEqualTo(expectedResult);
        assertThat(result2).isEqualTo(expectedResult2);

    }


}