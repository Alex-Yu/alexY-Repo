package com.javarush.test.level26.lesson15.big01.command;


import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulator;
import com.javarush.test.level26.lesson15.big01.CurrencyManipulatorFactory;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;
import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by AlexY on 09.11.2014.
 */
class WithdrawCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        //1.1
        String currency = ConsoleHelper.askCurrencyCode();
        //1.2
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currency);
        //1.3
        String ahtung = res.getString("specify.not.empty.amount");
        int amount = 0;
        while (amount <= 0)
        {
            try
            {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String s = ConsoleHelper.readString();
                amount = Integer.parseInt(s);
                if (amount <= 0)
                {
                    ConsoleHelper.writeMessage(ahtung);
                    continue;
                }
                if (!manipulator.isAmountAvailable(amount))
                {
                    amount = 0;
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                    continue;
                }

                Map<Integer, Integer> withdrawMap = manipulator.withdrawAmount(amount);
                if (withdrawMap.isEmpty())
                {
                    //Something is wrong
                    amount = 0;
                    continue;
                }
                for (Map.Entry<Integer, Integer> pair : withdrawMap.entrySet())
                {
                    int nominal = pair.getKey();
                    int nominalAmount = pair.getValue();
                    String out = String.format("\t %d - %d", nominal, nominalAmount);
                    ConsoleHelper.writeMessage(out);
                }
                String success = String.format(res.getString("success.format"), amount, currency);
                ConsoleHelper.writeMessage(success);
            } catch (NumberFormatException e)
            {
                ConsoleHelper.writeMessage(ahtung);
            } catch (NotEnoughMoneyException e) {
                amount = 0;
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }

        }
    }
}
