package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by AlexY on 09.11.2014.
 */
class DepositCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currency = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
        String[] twoDigits = ConsoleHelper.getValidTwoDigits(currency);
        int nominal = Integer.parseInt(twoDigits[0]);
        int banknotes = Integer.parseInt(twoDigits[1]);
        manipulator.addAmount(nominal, banknotes);
        String success = String.format(res.getString("success.format"), nominal * banknotes, currency);
        ConsoleHelper.writeMessage(success);
    }
}
