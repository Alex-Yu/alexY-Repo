package com.javarush.test.level28.lesson15.big01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by AlexY on 13.05.2015.
 */
public final class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleHelper() {

    }

    public static String input() {
        String s = "";
        try
        {
            s = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void output(String s) {
        System.out.println(s);
    }

    public static void close() {
        try
        {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
