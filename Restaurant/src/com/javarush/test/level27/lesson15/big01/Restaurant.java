package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Cook;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.kitchen.Waitor;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Алла on 13.11.2014.
 */
public class Restaurant
{
    private static final int ORDER_CREATING_INTERVAL = 100;
    private static final LinkedBlockingQueue<Order> QUEUE = new LinkedBlockingQueue<>();

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);

        Cook sasha = new Cook("Sasha");
        Cook vasya = new Cook("Vasya");
        Thread sashaThread = new Thread(sasha);
        Thread vasyaThread = new Thread(vasya);
        sashaThread.setDaemon(true);
        vasyaThread.setDaemon(true);
        sashaThread.start();
        vasyaThread.start();

        Waitor waitor = new Waitor();
        sasha.addObserver(waitor);
        vasya.addObserver(waitor);


        List<Tablet> tablets = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            tablets.add(new Tablet(i));
        }


        RandomOrderGeneratorTask generatorTask = new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL);
        Thread thread = new Thread(generatorTask);
        thread.start();

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){/*NOP*/}

        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();


    }

    public static LinkedBlockingQueue<Order> getQueue()
    {
        return QUEUE;
    }
}
