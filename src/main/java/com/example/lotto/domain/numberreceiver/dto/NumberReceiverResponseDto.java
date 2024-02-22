package com.example.lotto.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record NumberReceiverResponseDto(TicketDto ticket, String message) {

}
