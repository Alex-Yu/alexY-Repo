package com.javarush.test.level26.lesson15.big01;

/**
 * Created by AlexY on 09.11.2014.
 */
public enum Operation
{
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i < Operation.values().length && i > 0)
            return Operation.values()[i];
        else
            throw new IllegalArgumentException();
    }
}
