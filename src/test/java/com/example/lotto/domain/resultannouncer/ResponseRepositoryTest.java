package com.example.lotto.domain.resultannouncer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResponseRepositoryTest implements ResponseRepository{

    private final Map<String,ResultResponse> responseDatabase = new ConcurrentHashMap<>();

    @Override
    public ResultResponse save(ResultResponse resultResponse) {
        return responseDatabase.put(resultResponse.hash(), resultResponse);
    }

    @Override
    public boolean existsByHash(String hash) {
        return responseDatabase.containsKey(hash);
    }

    @Override
    public Optional<ResultResponse> findByHash(String hash) {
        return Optional.ofNullable(responseDatabase.get(hash));
    }
}