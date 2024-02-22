package com.example.lotto.domain.numberreceiver;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator {

    private static final int QUANTITY_OF_NUMBERS_FROM_USER = 6;
    private static final int MIN_VALUE_NUMBER_FROM_USER = 1;
    private static final int MAX_MALUE_NUMBER_FROM_USER = 99;

   List<ValidationResult> errors = new LinkedList<>();

   List<ValidationResult> validate(Set<Integer> numbersFromUser){
       if (!isNumberInRange(numbersFromUser)){
           errors.add(ValidationResult.NOT_IN_RANGE);
       }
       if (!isNumberSizeEqualSix(numbersFromUser)){
           errors.add(ValidationResult.NOT_SIX_NUMBERS_GIVEN);
       }
       return errors;
   }

   String createResultMessage(){
       return this.errors
               .stream()
               .map(ValidationResult -> ValidationResult.info)
               .collect(Collectors.joining(","));
   }

    boolean isNumberInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .allMatch(number -> number >= MIN_VALUE_NUMBER_FROM_USER &&
                        number <= MAX_MALUE_NUMBER_FROM_USER);

    }

    private boolean isNumberSizeEqualSix(Set<Integer> numbersFromUser) {
        return numbersFromUser.size() == QUANTITY_OF_NUMBERS_FROM_USER;
    }
}
