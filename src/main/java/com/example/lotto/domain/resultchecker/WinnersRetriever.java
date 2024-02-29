package com.example.lotto.domain.resultchecker;

import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


class WinnersRetriever {
    private static final int NUMBERS_WHEN_PLAYER_WON = 3;

    List<Player> retrievieWinners(List<Ticket> allTicketsByDate, Set<Integer> winningNumbers) {
        return allTicketsByDate.stream()
                .map(ticket -> {
                    Set<Integer> hitNumbers = calculateHits(winningNumbers, ticket);
                    return buildPlayer(ticket, hitNumbers);
                }).collect(Collectors.toList());
    }

    private Set<Integer> calculateHits(Set<Integer> winningNumbers, Ticket ticket){
        return ticket.numbers()
                .stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    private Player buildPlayer(Ticket ticket, Set<Integer> hitNumbers){
        Player.PlayerBuilder builder = Player.builder();
        if (isWinner(hitNumbers)){
            builder.isWinner(true);
        }
        return builder.hash(ticket.hash())
                .numbers(ticket.numbers())
                .hitNumbers(hitNumbers)
                .drawTime(ticket.drawDate())
                .build();
    }

    private boolean isWinner(Set<Integer> hitNubers){
        return hitNubers.size() >= NUMBERS_WHEN_PLAYER_WON;
    }


}
