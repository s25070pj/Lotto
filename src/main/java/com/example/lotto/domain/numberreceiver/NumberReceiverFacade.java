package com.example.lotto.domain.numberreceiver;

import com.example.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import com.example.lotto.domain.numberreceiver.dto.TicketDto;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor

public class NumberReceiverFacade {

    private final NumberValidator numberValidator;
    private final DrawDateGenerator drawDateGenerator;
    private final HashGenerable hashGenerator;
    private final TicketRepository ticketRepository;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {

        List<ValidationResult> validationResultList = numberValidator.validate(numbersFromUser);
        if (!validationResultList.isEmpty()) {
            String resultMessage = numberValidator.createResultMessage();
            return new NumberReceiverResponseDto(null, resultMessage);
        }

        LocalDateTime drawTime = drawDateGenerator.getNextDrawDate();
        String hash = hashGenerator.getHash();

        TicketDto generatedTicket = TicketDto.builder()
                .hash(hash)
                .numbers(numbersFromUser)
                .drawDate(drawTime)
                .build();

        Ticket savedTicket = Ticket.builder()
                .hash(generatedTicket.hash())
                .numbersFromUser(generatedTicket.numbers())
                .drawDate(generatedTicket.drawDate())
                .build();

        ticketRepository.save(savedTicket);

        return new NumberReceiverResponseDto(generatedTicket, ValidationResult.INPUT_SUCCESS.info);
    }


    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime date){
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        if (date.isAfter(nextDrawDate)){
            return Collections.emptyList();
        }
        return ticketRepository.findAllTicketsByDrawDate(date)
                .stream()
                .filter(ticket -> ticket.drawDate().isEqual(date))
                .map(ticket -> TicketDto.builder()
                        .hash(ticket.hash())
                                .numbers(ticket.numbersFromUser())
                                .drawDate(ticket.drawDate())
                                .build())
                .collect(Collectors.toList());
    }

    public LocalDateTime retrieveNextDrawDate(){
        return drawDateGenerator.getNextDrawDate();
    }

    public TicketDto findByHash(String hash){
        Ticket ticket = ticketRepository.findByHash(hash);
        return TicketDto.builder()
                .hash(ticket.hash())
                .numbers(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .build();
    }


}
