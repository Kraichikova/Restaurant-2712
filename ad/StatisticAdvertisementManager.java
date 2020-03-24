package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//будет предоставлять информацию из AdvertisementStorage в нужном нам виде. Синглтон.
/*
3. В StatisticAdvertisementManager создай два (или один) метода (придумать самостоятельно),
 которые из хранилища AdvertisementStorage достанут все необходимые данные -
  соответственно список активных и неактивных рекламных роликов.
Активным роликом считается тот, у которого есть минимум один доступный показ.
Неактивным роликом считается тот, у которого количество показов равно 0.
 */
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    private AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }


    public List<Advertisement> getActiveVideoSet() {
        List<Advertisement> list = new ArrayList<>();
        for (Object o : storage.list()) {
            if (((Advertisement) o).getHits() > 0) {
                list.add((Advertisement) o);
            }
        }
        Collections.sort(list, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return list;
    }

    public List<Advertisement> getArchiveVideoSet() {
        List<Advertisement> list = new ArrayList<>();
        for (Object o : storage.list()) {
            if (((Advertisement) o).getHits() == 0) {
                list.add((Advertisement) o);
            }
        }
        Collections.sort(list, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return list;
    }
}
