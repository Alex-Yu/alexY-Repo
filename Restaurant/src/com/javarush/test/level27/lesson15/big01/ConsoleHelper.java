package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алла on 13.11.2014.
 */
public class ConsoleHelper
{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException
    {
        String s = reader.readLine();

        return s;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        writeMessage("Today we have:");
        writeMessage(Dish.allDishesToString());
        writeMessage("Please make your choise:");

        List<Dish> dishes = new ArrayList<Dish>();
        while (true)
        {
            String dishName = readString();
            if (dishName.equalsIgnoreCase("exit"))
                break;

            boolean isDish = false;
            for (Dish dish : Dish.values()) {
                if (dishName.equals(dish.name())) {
                    isDish = true;
                    dishes.add(dish);
                    break;
                }
            }
            if (!isDish)
                writeMessage(dishName + " is not detected");

        }


        return dishes;
    }



}
