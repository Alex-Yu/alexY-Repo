package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by AlexY on 09.11.2014.
 */
class ExitCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        String ahtung = "Incorrect data!";
        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
            String s = ConsoleHelper.readString();
            if (!(s.equalsIgnoreCase(res.getString("yes")) || s.equalsIgnoreCase("n"))) {
                ConsoleHelper.writeMessage(ahtung);
                continue;
            } else if (s.equalsIgnoreCase("n")) {

                return;
            } else
            {
                ConsoleHelper.writeMessage(res.getString("thank.message"));
                return;
            }

        }
    }
}
