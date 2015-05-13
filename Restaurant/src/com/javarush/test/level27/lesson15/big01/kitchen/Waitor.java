package com.javarush.test.level27.lesson15.big01.kitchen;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Алла on 13.11.2014.
 */
public class Waitor implements Observer
{
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println(arg + " was cooked by " + o);
    }
}
