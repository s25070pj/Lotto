package com.example.lotto.domain.resultchecker;

import com.example.lotto.domain.numberreceiver.dto.TicketDto;
import com.example.lotto.domain.resultchecker.dto.PlayersDto;
import com.example.lotto.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.stream.Collectors;

class ResultCheckerMapper {
    static List<ResultDto> mapPlayersToResults(List<Player> players){
        return players.stream()
                .map(player -> ResultDto.builder()
                        .hash(player.hash())
                        .drawTime(player.drawTime())
                        .numbers(player.numbers())
                        .hitNumbers(player.hitNumbers())
                        .isWinner(player.isWinner())
                        .build())
                .collect(Collectors.toList());
    }

    static List<Ticket> mapFromTicketDto(List<TicketDto> allTicketsByDate){
        return allTicketsByDate.stream()
                .map(ticketDto -> Ticket.builder()
                        .drawDate(ticketDto.drawDate())
                        .hash(ticketDto.hash())
                        .numbers(ticketDto.numbers())
                        .build())
                .collect(Collectors.toList());
    }
}
