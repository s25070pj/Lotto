package com.example.lotto.domain.resultchecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRepositoryTest implements PlayerRepository {

    private final Map<String, Player> playersDatabase = new ConcurrentHashMap<>();

    @Override
    public List<Player> saveAll(List<Player> players) {
        players.forEach(player -> playersDatabase.put(player.hash(), player));
        return players;
    }

    @Override
    public Optional<Player> findById(String hash) {
        return Optional.ofNullable(playersDatabase.get(hash));
    }
}