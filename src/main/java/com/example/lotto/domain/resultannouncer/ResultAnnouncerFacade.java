package com.example.lotto.domain.resultannouncer;

import com.example.lotto.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.example.lotto.domain.resultannouncer.dto.ResultResponseDto;
import com.example.lotto.domain.resultchecker.ResultCheckerFacade;
import com.example.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@AllArgsConstructor
public class ResultAnnouncerFacade {

    private static final LocalTime RESULTS_ANNOUNCEMENT_TIME = LocalTime.of(12, 0).plusMinutes(5);
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResult(String hash) {
        if (responseRepository.existsByHash(hash)) {
            Optional<ResultResponse> cachedResultResponse = responseRepository.findByHash(hash);
            if (cachedResultResponse.isPresent()) {
                return new ResultAnnouncerResponseDto(ResultMapper.mapToDto(cachedResultResponse.get()), MessageResponse.ALREADY_CHECKED.message);
            }
        }

        ResultDto resultDto = resultCheckerFacade.findByHash(hash);
        if (resultDto == null) {
            return new ResultAnnouncerResponseDto(null, MessageResponse.HASH_DOES_NOT_EXIST_MESSAGE.message);
        }

        ResultResponseDto resultResponseDto = buildResponseDto(resultDto);
        responseRepository.save(buildResultResponse(resultResponseDto));
        if (responseRepository.existsByHash(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.WAIT_MESSAGE.message);

        }
        if (resultCheckerFacade.findByHash(hash).isWinner()) {
            return new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.WIN_MESSAGE.message);
        }
        return new ResultAnnouncerResponseDto(resultResponseDto, MessageResponse.LOSE_MESSAGE.message);

    }

    private static ResultResponse buildResultResponse(ResultResponseDto resultResponseDto) {
        return ResultResponse.builder()
                .hash(resultResponseDto.hash())
                .numbers(resultResponseDto.numbers())
                .hitNumbers(resultResponseDto.hitNumbers())
                .drawDate(resultResponseDto.drawDate())
                .isWinner(resultResponseDto.isWinner())
                .build();

    }

    private static ResultResponseDto buildResponseDto(ResultDto resultDto) {
        return ResultResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawTime())
                .isWinner(resultDto.isWinner())
                .build();
    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = LocalDateTime.of(resultDto.drawTime().toLocalDate(), RESULTS_ANNOUNCEMENT_TIME); //
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }



}
