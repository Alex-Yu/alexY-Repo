package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

/**
 * Created by AlexY on 09.11.2014.
 */
interface Command
{
    void execute() throws InterruptOperationException;
}
