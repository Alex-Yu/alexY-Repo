package com.javarush.test.level26.lesson15.big01;

import com.javarush.test.level26.lesson15.big01.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Created by AlexY on 09.11.2014.
 */
public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res =
                   ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");


    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try
        {
            String s = reader.readLine();
            if (s.equalsIgnoreCase(res.getString("operation.EXIT")))
                 throw new InterruptOperationException();

            return s;
        } catch (IOException e) {  }

        return "";
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        while(true)
        {
            writeMessage(res.getString("choose.currency.code"));
            String s = readString();
            if (s.length() == 3) {
                s = s.toUpperCase();
                return s;
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String ahtung = res.getString("invalid.data");
        while (true) {
            try
            {
                String enter = String.format(res.getString("choose.denomination.and.count.format"), "USD");
                writeMessage(enter);
                String[] s = readString().split(" ");
                if (s.length != 2) {
                    writeMessage(ahtung);
                    continue;
                }

                int nominal = Integer.parseInt(s[0]);
                int banknotes = Integer.parseInt(s[1]);

                if (nominal <= 0 || banknotes <= 0) {
                    writeMessage(ahtung);
                    continue;
                }

                String[] valid = {String.valueOf(nominal), String.valueOf(banknotes)};
                return valid;

            } catch (NumberFormatException e) {
                writeMessage(ahtung);
                continue;
            }
        }
    }

   public static Operation askOperation() throws InterruptOperationException {
       String ahtung = res.getString("invalid.data");
       while (true)
       {
           try
           {
               String enter = String.format("1 - %s, 2 - %s, 3 - %s, 4 - %s", res.getString("operation.INFO"),
                       res.getString("operation.DEPOSIT"), res.getString("operation.WITHDRAW"),
                       res.getString("operation.EXIT"));
               writeMessage(enter);
               writeMessage(res.getString("choose.operation"));
               int val = Integer.parseInt(readString());
               return Operation.getAllowableOperationByOrdinal(val);

           } catch (IllegalArgumentException e) {
               writeMessage(ahtung);
               continue;
           }
       }
   }

    public static void printExitMessage() {

        writeMessage(res.getString("the.end"));
    }
}
