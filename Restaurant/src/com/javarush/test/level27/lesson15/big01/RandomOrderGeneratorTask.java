package com.javarush.test.level27.lesson15.big01;

import java.util.List;

/**
 * Created by Алла on 20.11.2014.
 */
public class RandomOrderGeneratorTask implements Runnable
{
    private List<Tablet> tablets;
    private final int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval)
    {
        this.tablets = tablets;
        this.interval = interval;

    }

    @Override
    public void run()
    {
        int numberOfTablets = tablets.size();
        try
        {
            while (!Thread.currentThread().isInterrupted())
            {
                int tabletNumber = (int) (numberOfTablets * Math.random());
                tablets.get(tabletNumber).createTestOrder();
                Thread.sleep(interval);
            }
        } catch (InterruptedException e) { }
    }
}
