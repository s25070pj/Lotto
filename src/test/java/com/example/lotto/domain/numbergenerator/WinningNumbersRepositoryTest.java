package com.example.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class WinningNumbersRepositoryTest implements WinningNumbersRepository {

    private final Map<LocalDateTime, WinningNumbers> winningNumbersMap = new ConcurrentHashMap<>();
    @Override
    public Optional<WinningNumbers> findNumbersByDate(LocalDateTime date) {
        return Optional.ofNullable(winningNumbersMap.get(date));
    }

    @Override
    public boolean existsByDate(LocalDateTime nextDrawDate) {
        return winningNumbersMap.containsKey(nextDrawDate);
    }

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        return winningNumbersMap.put(winningNumbers.date(), winningNumbers);
    }
}