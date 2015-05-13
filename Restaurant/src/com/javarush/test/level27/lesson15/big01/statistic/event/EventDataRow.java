package com.javarush.test.level27.lesson15.big01.statistic.event;

import java.util.Date;

/**
 * Created by Алла on 16.11.2014.
 */
public interface EventDataRow
{
    EventType getType();
    Date getDate();
    int getTime();
}
