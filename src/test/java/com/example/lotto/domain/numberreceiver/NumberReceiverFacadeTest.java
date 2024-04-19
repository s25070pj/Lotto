package com.example.lotto.domain.numberreceiver;

import com.example.lotto.domain.AdjustableClock;
import com.example.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import com.example.lotto.domain.numberreceiver.dto.TicketDto;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberReceiverFacadeTest {

    TicketRepository ticketRepository = new TicketRepositoryTestImpl();

    Clock clock = Clock.systemUTC();


    @Test
    public void should_return_correct_response_when_user_input_six_numbers_in_range() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto ticket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();

        //when
        NumberReceiverResponseDto userResponse = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then

        NumberReceiverResponseDto expectedResponse = NumberReceiverResponseDto.builder()
                .ticket(ticket)
                .message(ValidationResult.INPUT_SUCCESS.info)
                .build();
        assertThat(userResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_input_six_numbers_but_one_number_is_out_of_range() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 111);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto ticket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();
        //when
        NumberReceiverResponseDto userResponse = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then

        NumberReceiverResponseDto expectedResponse = NumberReceiverResponseDto.builder()
                .ticket(null)
                .message(ValidationResult.NOT_IN_RANGE.info)
                .build();
        assertThat(userResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_input_six_numbers_but_one_number_is_out_of_range_and_is_negative() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, -1);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto ticket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();
        //when
        NumberReceiverResponseDto userResponse = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then

        NumberReceiverResponseDto expectedResponse = NumberReceiverResponseDto.builder()
                .ticket(null)
                .message(ValidationResult.NOT_IN_RANGE.info)
                .build();
        assertThat(userResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_failed_message_when_user_input_less_than_six_numbers() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto ticket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();
        //when
        NumberReceiverResponseDto userResponse = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then

        NumberReceiverResponseDto expectedResponse = NumberReceiverResponseDto.builder()
                .ticket(null)
                .message(ValidationResult.NOT_SIX_NUMBERS_GIVEN.info)
                .build();
        assertThat(userResponse).isEqualTo(expectedResponse);


    }


    @Test
    public void should_return_failed_message_when_user_input_more_than_six_numbers() {
        //given
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        DrawDateGenerator drawDateGenerator = new DrawDateGenerator(clock);
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();

        TicketDto ticket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .numbers(numbersFromUser)
                .drawDate(nextDrawDate)
                .build();
        //when
        NumberReceiverResponseDto userResponse = numberReceiverFacade.inputNumbers(numbersFromUser);
        //then

        NumberReceiverResponseDto expectedResponse = NumberReceiverResponseDto.builder()
                .ticket(null)
                .message(ValidationResult.NOT_SIX_NUMBERS_GIVEN.info)
                .build();
        assertThat(userResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void should_return_correct_hash() {
        //given
        HashGenerable hashGenerator = new HashGenerator();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        String response = numberReceiverFacade.inputNumbers(numbersFromUser).ticket().hash();
        //then
        assertThat(response).hasSize(36);
        assertThat(response).isNotNull();
    }

    @Test
    public void should_return_correct_draw_date() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 2, 23, 13, 0, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("Europe/London"));
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime testedDrawTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticket().drawDate();
        //then
        LocalDateTime expectedDrawTime = LocalDateTime.of(2024, 2, 24, 12, 0, 0);

        assertThat(testedDrawTime).isEqualTo(expectedDrawTime);
    }

    @Test
    public void should_return_next_Saturday_draw_date_when_date_is_Saturday_noon() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 2, 24, 12, 0, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("Europe/London"));
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime testedDrawTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticket().drawDate();
        //then
        LocalDateTime expectedDrawTime = LocalDateTime.of(2024, 3, 2, 12, 0, 0);

        assertThat(testedDrawTime).isEqualTo(expectedDrawTime);
    }

    @Test
    public void should_return_next_Saturday_draw_date_when_date_is_Saturday_afternoon() {
        //given
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 2, 24, 16, 30, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("Europe/London"));
        HashGenerable hashGenerator = new HashGeneratorTestImpl();
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        //when
        LocalDateTime testedDrawTime = numberReceiverFacade.inputNumbers(numbersFromUser).ticket().drawDate();
        //then
        LocalDateTime expectedDrawTime = LocalDateTime.of(2024, 3, 2, 12, 0, 0);

        assertThat(testedDrawTime).isEqualTo(expectedDrawTime);
    }

    @Test
    public void it_should_return_tickets_with_correct_draw_date() {
        //given
        HashGenerable hashGenerator = new HashGenerator();
        Instant fixedInstant = LocalDateTime.of(2024, 2, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of("Europe/London");
        AdjustableClock clock = new AdjustableClock(fixedInstant, zoneId);
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);


        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        clock.plusDays(1);
        NumberReceiverResponseDto numberReceiverResponseDto1 = numberReceiverFacade.inputNumbers(Set.of(1, 2, 66, 4, 5, 6));
        clock.plusDays(8);
        NumberReceiverResponseDto numberReceiverResponseDto2 = numberReceiverFacade.inputNumbers(Set.of(1, 2, 12, 4, 43, 6));
        clock.plusDays(1);

        TicketDto ticketDto = numberReceiverResponseDto.ticket();
        TicketDto ticketDto1 = numberReceiverResponseDto1.ticket();


        LocalDateTime dateTime = numberReceiverResponseDto.ticket().drawDate();


        //when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateTime);
        //then
        assertThat(allTicketsByDate).containsOnly(ticketDto, ticketDto1);
    }


    @Test
    public void it_should_return_empty_collections_if_there_are_no_tickets() {
        //given
        HashGenerable hashGenerator = new HashGenerator();
        Instant fixedInstant = LocalDateTime.of(2024, 2, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of("Europe/London");
        AdjustableClock clock = new AdjustableClock(fixedInstant, zoneId);
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);

        LocalDateTime dateTime = LocalDateTime.now(clock);
        //when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateTime);
        //then
        assertThat(allTicketsByDate).isEmpty();
    }


    @Test
    public void it_should_return_empty_collections_if_given_date_is_after_next_drawDate() {
        //given
        HashGenerable hashGenerator = new HashGenerator();
        Instant fixedInstant = LocalDateTime.of(2024, 2, 24, 12, 0, 0).toInstant(ZoneOffset.UTC);
        ZoneId zoneId = ZoneId.of("Europe/London");
        AdjustableClock clock = new AdjustableClock(fixedInstant, zoneId);
        NumberReceiverFacade numberReceiverFacade = new NumberReceivierConfiguration().numberReceiverFacade(hashGenerator, clock, ticketRepository);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(numbersFromUser);

        LocalDateTime dateTime = LocalDateTime.of(2024, 2, 29, 12, 0, 0);
        //when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(dateTime);
        //then
        assertThat(allTicketsByDate).isEmpty();
    }
}