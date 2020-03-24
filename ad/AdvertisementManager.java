package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
AdvertisementManager - у каждого планшета будет свой объект менеджера,
который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
Он также будет взаимодействовать с плеером и отображать ролики.

2.2. Подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду.
 (Пока делать не нужно, сделаем позже).
2.3. Если нет рекламных видео, которые можно показать посетителю, то бросить NoVideoAvailableException,
которое перехватить в оптимальном месте (подумать, где это место) и с уровнем Level.INFO логировать
фразу "No video is available for the order " + order
2.4. Отобразить все рекламные ролики, отобранные для показа, в порядке уменьшения стоимости показа
 одного рекламного ролика в копейках. Вторичная сортировка - по увеличению стоимости показа одной
 секунды рекламного ролика в тысячных частях копейки.
Используйте метод Collections.sort
(Тоже пока делать не нужно, сделаем позже).

Пример для заказа [Water]:
First Video is displaying... 50, 277
где First Video - название рекламного ролика
где 50 - стоимость показа одного рекламного ролика в копейках
где 277 - стоимость показа одной секунды рекламного ролика в тысячных частях копейки (равно 0.277 коп)
Используйте методы из класса Advertisement.

 */
public class AdvertisementManager {
    private final AdvertisementStorage storage=AdvertisementStorage.getInstance();
    int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    /*
    В случае, если список видео для воспроизведения пуст,
     должно быть брошено исключение NoVideoAvailableException из метода processVideos().
     Подобрать список видео из доступных, просмотр которых обеспечивает максимальную выгоду.
     Этот набор должен удовлетворять следующим требованиям:
    1. сумма денег, полученная от показов, должна быть максимальной из всех возможных вариантов
    2. общее время показа рекламных роликов НЕ должно превышать время приготовления блюд для текущего заказа;
    3. для одного заказа любой видео-ролик показывается не более одного раза;
    4. если существует несколько вариантов набора видео-роликов с одинаковой суммой денег, полученной от показов, то:
    4.1. выбрать тот вариант, у которого суммарное время максимальное;
    4.2. если суммарное время у этих вариантов одинаковое, то выбрать вариант с минимальным количеством роликов;
    5. количество показов у любого рекламного ролика из набора - положительное число.

    Также не забудь реализовать п.2.4 из предыдущего задания
     (вывести на экран все подходящие ролики в порядке уменьшения стоимости показа одного рекламного ролика в копейках.
    Вторичная сортировка - по увеличению стоимости показа одной секунды рекламного ролика в тысячных частях копейки).
    Для каждого показанного видео-ролика должен быть вызван метод revalidate().

     */
    public void processVideos() {
        Collections.sort(storage.list(), new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int result = Long.compare(o1.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
                if (result != 0)
                    return -result;

//                long oneSecondCost1 = o1.getAmountPerOneDisplaying() * 1000 / o1.getDuration();
//                long oneSecondCost2 = o2.getAmountPerOneDisplaying() * 1000 / o2.getDuration();
//                    result = Long.compare(oneSecondCost1, oneSecondCost2);
//                    if (result != 0)
//                        return result;
                int duration1 = o1.getDuration();
                int duration2 = o2.getDuration();
                result = Integer.compare(duration1, duration2);
                if (result != 0)
                    return -result;
                int countVideo1 = o1.getHits();
                int countVideo2 = o2.getHits();
                return Integer.compare(countVideo1, countVideo2);
            }
        });

        int timeLeft = timeSeconds;
        ArrayList<Advertisement> optimalVideoSet = new ArrayList<>();
        long amount = 0;
        int totalDuration = 0;
        for (Advertisement advertisement : storage.list()) {
            if (timeLeft < advertisement.getDuration()) {
                continue;
            }
            if (advertisement.getHits() <= 0) {
                continue;
            }
            timeLeft -= advertisement.getDuration();
            advertisement.revalidate();
            optimalVideoSet.add(advertisement);
            amount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
        }
        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(
                optimalVideoSet,
                amount,
                totalDuration));
        for (Advertisement advertisement : optimalVideoSet) {
            ConsoleHelper.writeMessage(advertisement.getName() + " is displaying... "
                    + advertisement.getAmountPerOneDisplaying() + ", "
                    + advertisement.getAmountPerOneDisplaying() * 1000 / advertisement.getDuration());
        }

        if (timeLeft == timeSeconds) {
            throw new NoVideoAvailableException();
        }
    }
    /*
    public void processVideos() throws NoVideoAvailableException {
        if (storage.list().isEmpty()) throw new NoVideoAvailableException();
        List<Advertisement> video = new ArrayList<>();
        for (Object video1 : storage.list()) {
            Advertisement adv = (Advertisement) video1;
            video.add(adv);
        }
        Collections.sort(video, (Comparator.comparingInt(Advertisement::getDuration)));
        Collections.sort(video, ((o1, o2) -> (int) (o1.getAmountPerOneDisplaying() - o2.getAmountPerOneDisplaying())));
        Collections.reverse(video);
        int freetime = timeSeconds;
        for (Advertisement vid : video) {
            if (vid.getDuration() <=freetime && vid.getAmountPerOneDisplaying()>0) {
                StatisticManager.getInstance().register(new VideoSelectedEventDataRow(optimalVideoSet, amount, totalDuration));
                System.out.println(vid.getName()+" is displaying... "+vid.getAmountPerOneDisplaying()+", "+vid.getAmountPerOneDisplaying()*1000/vid.getDuration());
                vid.revalidate();
                freetime = freetime - vid.getDuration();
            }
        }
    }

     */
}
