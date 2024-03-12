package com.example.lotto.domain.resultannouncer;

import com.example.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.example.lotto.domain.resultannouncer.dto.ResultResponseDto;
import com.example.lotto.domain.resultchecker.ResultCheckerFacade;
import com.example.lotto.domain.resultchecker.dto.ResultDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResultAnnouncerFacadeTest {

    ResponseRepository responseRepository = new ResponseRepositoryTest();
    ResultCheckerFacade resultCheckerFacade = Mockito.mock(ResultCheckerFacade.class);

    @Test
    public void it_should_return_response_with_lose_message_if_ticket_is_not_winning_ticket() {
        //given
        String hash = "001";
        LocalDateTime drawDate = LocalDateTime.of(2024, 3, 2, 12, 0, 0);
        Clock clock = Clock.systemUTC();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);

        ResultDto resultDto = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2))
                .isWinner(false)
                .drawTime(drawDate)
                .build();

        Mockito.when(resultCheckerFacade.findByHash(hash)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2))
                .isWinner(false)
                .drawDate(drawDate)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.LOSE_MESSAGE.message);

        assertThat(expectedResult).isEqualTo(resultAnnouncerResponseDto);
    }


    @Test
    public void it_should_return_response_with_win_message_if_ticket_is_winning_ticket() {
        //given
        String hash = "001";
        LocalDateTime drawDate = LocalDateTime.of(2024, 3, 2, 12, 0, 0);
        Clock clock = Clock.systemUTC();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);

        ResultDto resultDto = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4))
                .isWinner(true)
                .drawTime(drawDate)
                .build();

        Mockito.when(resultCheckerFacade.findByHash(hash)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4))
                .isWinner(true)
                .drawDate(drawDate)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.WIN_MESSAGE.message);

        assertThat(expectedResult).isEqualTo(resultAnnouncerResponseDto);
    }


    @Test
    public void it_should_return_response_with_wait_message_if_date_is_before_announcement_time() {

        //given
        String hash = "001";
        LocalDateTime drawDate = LocalDateTime.of(2024, 3, 9, 12, 0, 0);
        Clock clock = Clock.fixed(LocalDateTime.of(2024,3,8,12,0,0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);

        ResultDto resultDto = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4))
                .isWinner(true)
                .drawTime(drawDate)
                .build();

        Mockito.when(resultCheckerFacade.findByHash(hash)).thenReturn(resultDto);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultResponseDto resultResponseDto = ResultResponseDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4))
                .isWinner(true)
                .drawDate(drawDate)
                .build();

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.WAIT_MESSAGE.message);

        assertThat(expectedResult).isEqualTo(resultAnnouncerResponseDto);

    }

    @Test
    public void it_should_return_response_with_hash_does_not_exist_message_if_hash_does_not_exist() {
        //given
        String hash = "001";
        LocalDateTime drawDate = LocalDateTime.of(2024, 3, 2, 12, 0, 0);
        Clock clock = Clock.systemUTC();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);

        Mockito.when(resultCheckerFacade.findByHash(hash)).thenReturn(null);
        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(null, MessageResponse.HASH_DOES_NOT_EXIST_MESSAGE.message);

        assertThat(expectedResult).isEqualTo(resultAnnouncerResponseDto);

    }

    @Test
    public void it_should_return_response_with_hash_does_not_exist_message_if_response_is_not_saved_to_db_yet() {
        //given
        String hash = "001";
        LocalDateTime drawDate = LocalDateTime.of(2024, 3, 2, 12, 0, 0);
        Clock clock = Clock.systemUTC();
        ResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultCheckerFacade, responseRepository, clock);

        ResultDto resultDto = ResultDto.builder()
                .hash(hash)
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1,2,3,4))
                .isWinner(true)
                .drawTime(drawDate)
                .build();

        Mockito.when(resultCheckerFacade.findByHash(hash)).thenReturn(resultDto);

        ResultAnnouncerResponseDto resultAnnouncerResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultAnnouncerResponseDto1.resultResponseDto().hash();

        //when
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then

        ResultAnnouncerResponseDto expectedResult = new ResultAnnouncerResponseDto(resultAnnouncerResponseDto.resultResponseDto(), MessageResponse.ALREADY_CHECKED.message);

        assertThat(expectedResult).isEqualTo(resultAnnouncerResponseDto);
    }


}