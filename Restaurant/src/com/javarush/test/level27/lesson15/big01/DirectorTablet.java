package com.javarush.test.level27.lesson15.big01;


import com.javarush.test.level27.lesson15.big01.ad.Advertisement;
import com.javarush.test.level27.lesson15.big01.ad.StatisticAdvertisementManager;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Алла on 16.11.2014.
 */
public class DirectorTablet
{
    public void printAdvertisementProfit() {
        Map<Date, Long> unsortedAdvertisementProfitMap = StatisticEventManager.getInstance().getAdvertisementProfit();
        NavigableMap<Date, Long> advertisementProfitMap = new TreeMap<Date, Long>();
        advertisementProfitMap.putAll(unsortedAdvertisementProfitMap);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        float totalAmount = 0;
        if (!advertisementProfitMap.isEmpty()) {
            for (Map.Entry<Date, Long> pair : advertisementProfitMap.descendingMap().entrySet()) {
                Date date = pair.getKey();
                Float amount = pair.getValue() / 100f;
                ConsoleHelper.writeMessage(String.format("%s - %.2f", sdf.format(date), amount));
                totalAmount += amount;
            }
            ConsoleHelper.writeMessage(String.format("Total - %.2f\n", totalAmount));
        }
    }

    public void printCookWorkloading() {
        Map<Date, Map<String, Integer>> unsortedcookWorkloadingMap = StatisticEventManager.getInstance().getCookWorkloading();
        NavigableMap<Date, Map<String, Integer>> cookWorkloadingMap = new TreeMap<Date, Map<String, Integer>>();
        cookWorkloadingMap.putAll(unsortedcookWorkloadingMap);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        if (!cookWorkloadingMap.isEmpty()) {
            for (Map.Entry<Date, Map<String, Integer>> pair : cookWorkloadingMap.descendingMap().entrySet()) {
                Date date = pair.getKey();
                Map<String, Integer> cookTimeMap = pair.getValue();
                Map<String, Integer> sortedCookTimeMap = new TreeMap();
                sortedCookTimeMap.putAll(cookTimeMap);
                ConsoleHelper.writeMessage(sdf.format(date));
                for (Map.Entry<String, Integer> innerPair : sortedCookTimeMap.entrySet())
                {
                    String cookName = innerPair.getKey();
                    int cookingTimeSeconds = innerPair.getValue();
                    int cookingTimeMinutes = cookingTimeSeconds % 60 == 0 ? cookingTimeSeconds / 60 : cookingTimeSeconds / 60 + 1;
                    if (cookingTimeMinutes <= 0)
                        continue;
                    ConsoleHelper.writeMessage(String.format("%s - %d min", cookName, cookingTimeMinutes));
                }
                ConsoleHelper.writeMessage("");
            }
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> activeVideos = StatisticAdvertisementManager.getInstance().getActiveVideos();
        if (activeVideos == null)
            return;

        Collections.sort(activeVideos, new Comparator<Advertisement>()
        {
            @Override
            public int compare(Advertisement o1, Advertisement o2)
            {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement video : activeVideos) {
            String out = String.format("%s - %d", video.getName(), video.getHits());
            ConsoleHelper.writeMessage(out);
        }

    }

    public void printArchivedVideoSet() {
        List<Advertisement> archivedVideos = StatisticAdvertisementManager.getInstance().getPassiveVideos();
        if (archivedVideos == null)
            return;


        Collections.sort(archivedVideos, new Comparator<Advertisement>()
        {
            @Override
            public int compare(Advertisement o1, Advertisement o2)
            {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        for (Advertisement video : archivedVideos) {
            ConsoleHelper.writeMessage(video.getName());
        }
    }
}
