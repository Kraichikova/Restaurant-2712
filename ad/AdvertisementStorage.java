package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

//AdvertisementStorage - хранилище рекламных роликов.
//Singleton
public class AdvertisementStorage {
    private static AdvertisementStorage advertisementStorage;
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
    }

    public static AdvertisementStorage getInstance() {
        if (advertisementStorage == null) {
            advertisementStorage = new AdvertisementStorage();
        }
        return advertisementStorage;
    }

    public List<Advertisement> list() { // вернет список всех существующих доступных видео.
        return this.videos;
    }

    public void add(Advertisement advertisement) { // добавит новое видео в список videos.
        this.videos.add(advertisement);
    }

}
