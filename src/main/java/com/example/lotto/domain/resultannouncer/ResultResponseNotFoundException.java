package com.example.lotto.domain.resultannouncer;

public class ResultResponseNotFoundException extends RuntimeException{
    public ResultResponseNotFoundException(String message) {
        super(message);
    }
}
