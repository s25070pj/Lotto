package com.example.lotto.domain.resultannouncer;

import com.example.lotto.domain.resultannouncer.dto.ResultResponseDto;

public class ResultMapper {

    static ResultResponseDto mapToDto(ResultResponse resultResponse){
        return ResultResponseDto.builder()
                .hash(resultResponse.hash())
                .numbers(resultResponse.numbers())
                .hitNumbers(resultResponse.hitNumbers())
                .drawDate(resultResponse.drawDate())
                .isWinner(resultResponse.isWinner())
                .build();
    }

}
