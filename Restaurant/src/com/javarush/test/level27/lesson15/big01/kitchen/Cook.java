package com.javarush.test.level27.lesson15.big01.kitchen;


import com.javarush.test.level27.lesson15.big01.Restaurant;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Алла on 13.11.2014.
 */
public class Cook extends Observable implements Runnable
{
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name)
    {
        this.name = name;
        setQueue(Restaurant.getQueue());
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                if (queue.size() > 0 && !isBusy())
                {
                    new Thread()
                    {
                        public void run()
                        {
                            startCookingOrder(queue.poll());
                        }
                    }.start();
                }
                Thread.sleep(10);
            }
        }
        catch (InterruptedException e)
        {/*NOP*/}
    }





    public void setQueue(LinkedBlockingQueue<Order> queue)
    {
        this.queue = queue;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        int cookingTime = order.getTotalCookingTime() * 60;                     //время приготовления заказа в сек
        System.out.format("Start cooking - %s, cooking time %dmin", order, order.getTotalCookingTime());
        System.out.println();
        //задержка на приготовление
        try {
            Thread.sleep(cookingTime / 6);                  // min x 10
        } catch (InterruptedException e) {/*NOP*/}
        //регистрируем событие о приготовлении
        EventDataRow cookedOrderEvent = new CookedOrderEventDataRow(order.getTablet().toString(), name, cookingTime, order.getDishes());
        StatisticEventManager.getInstance().register(cookedOrderEvent);

        setChanged();
        notifyObservers(order);
        busy = false;
    }

    public boolean isBusy()
    {
        return busy;
    }
}
