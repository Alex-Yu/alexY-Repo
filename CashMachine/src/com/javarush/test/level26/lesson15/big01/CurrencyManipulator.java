package com.javarush.test.level26.lesson15.big01;


import com.javarush.test.level26.lesson15.big01.exception.NotEnoughMoneyException;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by AlexY on 09.11.2014.
 */
public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
        denominations = new TreeMap<Integer, Integer>(Collections.reverseOrder());

    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        denominations.put(denomination, count);

    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet()) {
            sum += pair.getKey() * pair.getValue();
        }

        return sum;
    }

    public boolean hasMoney() {

        return denominations.size() > 0 ? true : false;
    }

    public boolean isAmountAvailable(int expectedAmount) {

        return expectedAmount <= getTotalAmount() ? true : false;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {
        try
        {
            Map<Integer, Integer> withdraw = new TreeMap<Integer, Integer>(Collections.reverseOrder());

            boolean firstLoop = true;
            int minNominal = 0;
            while (withdraw.size() > 0 || firstLoop)
            {
                if (!firstLoop)
                {
                    minNominal = Collections.min(withdraw.keySet());
                    expectedAmount += minNominal;
                    int minAmount = withdraw.get(minNominal);
                    if (minAmount > 1)
                    {
                        withdraw.put(minNominal, --minAmount);
                    } else
                    {
                        withdraw.remove(minNominal);
                    }
                }

                for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
                {
                    int nominal = pair.getKey();
                    if (!firstLoop && minNominal <= nominal)
                        continue;
                    if (expectedAmount < nominal)
                        continue;
                    int nominalAmount = pair.getValue();
                    if (nominalAmount <= 0)
                        continue;

                    int expectedNominalAmount = expectedAmount / nominal;
                    if (expectedNominalAmount > nominalAmount)
                        expectedNominalAmount = nominalAmount;
                    withdraw.put(nominal, expectedNominalAmount);
                    if (expectedNominalAmount * nominal == expectedAmount)
                    {
                        for (Map.Entry<Integer, Integer> pairW : withdraw.entrySet())
                        {
                            int wNominal = pairW.getKey();
                            int wAmount = pairW.getValue();
                            int oldAmount = denominations.get(wNominal);
                            denominations.put(wNominal, oldAmount - wAmount);
                        }
                        return withdraw;
                    } else
                    {
                        expectedAmount = expectedAmount - (expectedNominalAmount * nominal);
                    }
                }

                firstLoop = false;
            }
            throw new NotEnoughMoneyException();
        }
        catch (ConcurrentModificationException e) { }

        return Collections.emptyMap();
    }
}
