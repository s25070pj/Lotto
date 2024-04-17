package com.example.lotto.domain.numberreceiver;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface TicketRepository {

    Collection<Ticket> findAllTicketsByDrawDate(LocalDateTime drawDate);

    Ticket findByHash(String hash);

    Ticket save(Ticket savedTicket);


}
