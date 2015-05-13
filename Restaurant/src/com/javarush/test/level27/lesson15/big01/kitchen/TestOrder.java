package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алла on 20.11.2014.
 */
public class TestOrder extends Order
{

    public TestOrder(Tablet tablet) throws IOException
    {
        super(tablet);
    }

    @Override
    protected void initDishes()
    {
        final int maxNumberOfDishes = 3;
        int numberOfDishes = 1 + (int) (Math.random()*maxNumberOfDishes);
        dishes = new ArrayList<>(numberOfDishes);
        for (int i = 0; i < numberOfDishes; i++) {
            int dishOrdinal = (int) (Dish.values().length * Math.random());
            Dish dish = Dish.values()[dishOrdinal];
            dishes.add(dish);
        }
    }
}
