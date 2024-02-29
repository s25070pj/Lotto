package com.example.lotto.domain.resultchecker;

import com.example.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.example.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import com.example.lotto.domain.numberreceiver.NumberReceiverFacade;
import com.example.lotto.domain.numberreceiver.dto.TicketDto;
import com.example.lotto.domain.resultchecker.dto.PlayersDto;
import com.example.lotto.domain.resultchecker.dto.ResultDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {

    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;
    NumberReceiverFacade numberReceiverFacade;
    PlayerRepository playerRepository;
    WinnersRetriever winnersRetriever;



    public PlayersDto generateWinners() {
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapFromTicketDto(allTicketsByDate);
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.getWinningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }

        List<Player> players = winnersRetriever.retrievieWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);

        return PlayersDto.builder()
                .results(ResultCheckerMapper.mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }

    public ResultDto findByHash(String hash) {
        Player player = playerRepository.findById(hash).orElseThrow(() -> new PlayerNotFoundException("Player not found!"));
        return ResultDto.builder()
                .hash(hash)
                .drawTime(player.drawTime())
                .numbers(player.numbers())
                .hitNumbers(player.hitNumbers())
                .isWinner(player.isWinner())
                .build();

    }


}

