package com.javarush.test.level27.lesson15.big01.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алла on 29.11.2014.
 */
public final class StatisticAdvertisementManager
{
    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager()
    {

    }

    public static StatisticAdvertisementManager getInstance() {

        return instance;
    }

    public List<Advertisement> getActiveVideos () {
        List<Advertisement> allVideos = advertisementStorage.list();
        List<Advertisement> activeVideos = new ArrayList<>();

        for (Advertisement someVideo : allVideos) {
            if (someVideo.getHits() >= 1) {
                activeVideos.add(someVideo);
            }
        }
        return activeVideos;
    }

    public List<Advertisement> getPassiveVideos () {
        List<Advertisement> allVideos = advertisementStorage.list();
        List<Advertisement> passiveVideos = new ArrayList<>();

        for (Advertisement someVideo : allVideos) {
            if (someVideo.getHits() == 0) {
                passiveVideos.add(someVideo);
            }
        }

        return passiveVideos;
    }
}
