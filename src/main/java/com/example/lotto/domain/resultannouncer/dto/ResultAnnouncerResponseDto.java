package com.example.lotto.domain.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultAnnouncerResponseDto(ResultResponseDto resultResponseDto,
                                         String message){
}
