package com.example.lotto.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
 record Player(String hash,
        Set<Integer> numbers,
        Set<Integer> hitNumbers,
        LocalDateTime drawTime,
        boolean isWinner) {
}
