package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by AlexY on 09.11.2014.
 */
class InfoCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String ahtung = res.getString("no.money");
        Collection<CurrencyManipulator> currencyManipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        if (currencyManipulators.size() == 0)
        {
            ConsoleHelper.writeMessage(ahtung);
            return;
        }

        int noMoneyCounter = 0;
        for (CurrencyManipulator manipulator : currencyManipulators)
        {
            if (!manipulator.hasMoney()) {
                noMoneyCounter++;
                continue;
            }
            String currency = manipulator.getCurrencyCode();
            int sum = manipulator.getTotalAmount();
            String out = String.format(" %s - %d", currency, sum);
            ConsoleHelper.writeMessage(out);
        }
        if (noMoneyCounter == currencyManipulators.size())
            ConsoleHelper.writeMessage(ahtung);
    }
}
