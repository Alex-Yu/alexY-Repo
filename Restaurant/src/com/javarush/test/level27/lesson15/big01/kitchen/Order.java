package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алла on 13.11.2014.
 */
public class Order
{
    private Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException
    {
        this.tablet = tablet;
        initDishes();
    }

    public int getTotalCookingTime() {
        int totalCookingTime = 0;
        for (Dish dish : dishes) {
            totalCookingTime += dish.getDuration();
        }

        return totalCookingTime;
    }

    public boolean isEmpty()
    {
        return dishes.isEmpty();
    }

    @Override
    public String toString()
    {
        if (dishes.isEmpty())
            return "";
        String out = String.format("Your order: %s of %s", dishes, tablet);
        return out;
    }

    public List<Dish> getDishes()
    {
        return dishes;
    }

    protected void initDishes() throws IOException {
        dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public Tablet getTablet()
    {
        return tablet;
    }
}
