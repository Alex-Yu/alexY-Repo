package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.Operation;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AlexY on 09.11.2014.
 */
public final class CommandExecutor
{
    private static Map<Operation, Command> commandMap = new HashMap<Operation, Command>();
    static {
        commandMap.put(Operation.LOGIN, new LoginCommand());
        commandMap.put(Operation.INFO, new InfoCommand());
        commandMap.put(Operation.DEPOSIT, new DepositCommand());
        commandMap.put(Operation.WITHDRAW, new WithdrawCommand());
        commandMap.put(Operation.EXIT, new ExitCommand());
    }

    private CommandExecutor() {

    }

    public static final void execute(Operation operation) throws InterruptOperationException
    {
        Command command = commandMap.get(operation);
        command.execute();
    }
}
