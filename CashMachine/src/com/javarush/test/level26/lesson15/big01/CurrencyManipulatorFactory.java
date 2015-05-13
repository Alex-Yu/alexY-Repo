package com.javarush.test.level26.lesson15.big01;

import java.util.*;

/**
 * Created by AlexY on 09.11.2014.
 */
public class CurrencyManipulatorFactory
{
    private static Map<String, CurrencyManipulator> currencyManipulators = new HashMap<String, CurrencyManipulator>();

    private CurrencyManipulatorFactory() {

    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {

        if (!currencyManipulators.containsKey(currencyCode))
            currencyManipulators.put(currencyCode, new CurrencyManipulator(currencyCode));

        return currencyManipulators.get(currencyCode);
    }

    public static Collection getAllCurrencyManipulators() {

        return currencyManipulators.values();
    }
}
