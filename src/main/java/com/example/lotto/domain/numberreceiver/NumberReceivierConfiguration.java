package com.example.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;


@Configuration
public class NumberReceivierConfiguration {
    @Bean
    Clock clock(){
        return Clock.systemUTC();
    }

    @Bean
    HashGenerable hashGenerable (){
        return new HashGenerator();
    }

    @Bean
    TicketRepository ticketRepository() {
        return new TicketRepository() {
            @Override
            public Collection<Ticket> findAllTicketsByDrawDate(LocalDateTime drawDate) {
                return null;
            }

            @Override
            public Ticket findByHash(String hash) {
                return null;
            }

            @Override
            public Ticket save(Ticket savedTicket) {
                return null;
            }
        };
    }



    @Bean NumberReceiverFacade numberReceiverFacade(HashGenerable hashGenerator, Clock clock, TicketRepository ticketRepository) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        return new NumberReceiverFacade(numberValidator, drawDateGenerator, hashGenerator, ticketRepository);
    }




}
