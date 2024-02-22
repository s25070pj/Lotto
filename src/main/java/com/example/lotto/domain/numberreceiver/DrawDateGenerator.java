package com.example.lotto.domain.numberreceiver;

import java.time.*;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class DrawDateGenerator {
    public static final LocalTime DRAW_TIME = LocalTime.of(12, 0, 0);
    public static final TemporalAdjuster NEXT_DRAW_DATE = TemporalAdjusters.next(DayOfWeek.SATURDAY);
    private final Clock clock;

    public DrawDateGenerator(Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getNextDrawDate() {
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        if (isSaturdayAndBeforeNoon(currentDateTime)) {
            return LocalDateTime.of(currentDateTime.toLocalDate(), DRAW_TIME);
        }
        LocalDateTime drawDate = currentDateTime.with(NEXT_DRAW_DATE);
        return LocalDateTime.of(drawDate.toLocalDate(), DRAW_TIME);
    }

    private boolean isSaturdayAndBeforeNoon(LocalDateTime currentDateTime) {
        return currentDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                currentDateTime.toLocalTime().isBefore(DRAW_TIME);
    }
}
