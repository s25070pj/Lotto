package com.example.lotto.domain.resultannouncer;

import java.util.Optional;

public interface ResponseRepository {

    ResultResponse save(ResultResponse resultResponse);

    boolean existsByHash(String hash);

    Optional<ResultResponse> findByHash(String hash);

}
