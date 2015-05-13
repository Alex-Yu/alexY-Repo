package com.javarush.test.level26.lesson15.big01.command;

import com.javarush.test.level26.lesson15.big01.CashMachine;
import com.javarush.test.level26.lesson15.big01.ConsoleHelper;
import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.util.ResourceBundle;


/**
 * Created by AlexY on 11.11.2014.
 */
class LoginCommand implements Command
{
    private ResourceBundle validCreditCards =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        String message = res.getString("specify.data");
        for( ; ; ) {
            ConsoleHelper.writeMessage(message);
            String[] data = ConsoleHelper.readString().split(" ");
            if (data.length != 2) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                message = res.getString("try.again.or.exit");
                continue;
            }
            String enteredCard = data[0];
            boolean isCardValid = enteredCard.matches("^\\d{12}$");
            String enteredPin = data[1];
            boolean isPinValid = enteredPin.matches("^\\d{4}$");

            if (!(isCardValid && isPinValid)) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                message = res.getString("try.again.or.exit");
                continue;
            } else
            {
                for (String card : validCreditCards.keySet())
                {
                    if (enteredCard.equals(card))
                    {
                        String pin = validCreditCards.getString(card);
                        if (enteredPin.equals(pin))
                        {
                            String success = String.format(res.getString("success.format"), enteredCard);
                            ConsoleHelper.writeMessage(success);
                            return;
                        }

                    }
                }

                String fail = String.format(res.getString("not.verified.format"), enteredCard);
                ConsoleHelper.writeMessage(fail);
                message = res.getString("try.again.or.exit");
                continue;
            }


        }

    }

}
