package com.javarush.test.level27.lesson15.big01.kitchen;

/**
 * Created by Алла on 13.11.2014.
 */
public enum Dish
{
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public static String allDishesToString() {
        if (values().length == 0)
            return "";
        StringBuilder dishes = new StringBuilder("");
        for (Dish dish : values())
            dishes.append(dish.name()).append(", ");
        dishes.delete(dishes.length() - 2, dishes.length());      //delete ", " at the end

        return dishes.toString();

    }


}
