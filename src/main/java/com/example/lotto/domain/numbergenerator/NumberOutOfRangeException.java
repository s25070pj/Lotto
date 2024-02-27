package com.example.lotto.domain.numbergenerator;

public class NumberOutOfRangeException extends IllegalStateException{
    public NumberOutOfRangeException(String message) {
        super(message);
    }
}
