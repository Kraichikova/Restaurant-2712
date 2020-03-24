package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Map;
import java.util.TreeMap;

/*
Давай подумаем что нужно сделать, чтобы директор мог посмотреть:
1. какую сумму заработали на рекламе, сгруппировать по дням;
2. загрузка (рабочее время) повара, сгруппировать по дням;
3. список активных роликов и оставшееся количество показов по каждому;
4. список неактивных роликов (с оставшемся количеством показов равным нулю).
 */
public class DirectorTablet {
    StatisticManager statisticManager = StatisticManager.getInstance();

    /*вывести в консоль в убывающем порядке даты и суммы.
    Для каждой даты из хранилища событий, для которой есть показанная реклама,
    должна выводится сумма прибыли за показы рекламы для этой даты.
    В конце вывести слово Total и общую сумму.
    Пример:
    14-May-2013 - 2.50
    13-May-2013 - 1.02
    12-May-2013 - 543.98
    Total - 547.50
     */
    public void printAdvertisementProfit(){
        TreeMap<String,Long> adsRevenueByDate = statisticManager.getAdsRevenueByDate();
        Long total = 0L;
        for (Map.Entry<String,Long> entry : adsRevenueByDate.entrySet()){
            String date = entry.getKey();
            Double profitInRubles = entry.getValue()/100.0;
            ConsoleHelper.writeMessage(String.format("%s - %.2f", date, profitInRubles));
            total += entry.getValue();
        }
        ConsoleHelper.writeMessage(String.format("%s - %.2f", "Total", total/100.0));
        ConsoleHelper.writeMessage("");
    }
    /*
        5. Реализуем логику метода printCookWorkloading в классе DirectorTablet.
    Используя метод из предыдущего пункта вывести в консоль в убывающем порядке даты,
    имена поваров и время работы в минутах (округлить в большую сторону).
    Для каждой даты из хранилища событий, для которой есть запись о работе повара,
    должна выводится продолжительность работы в минутах для этой даты.
    Если повар не работал в какой-то из дней, то с пустыми данными его НЕ выводить (см. 13-May-2013)
    Поваров сортировать по имени

    Пример:
    14-May-2013
    Ivanov - 60 min
    Petrov - 35 min

    13-May-2013
    Ivanov - 129 min

    12-May-2013
    Ivanov - 6 min
    Petrov - 5 min
    */
    public void printCookWorkloading(){
        TreeMap<String, TreeMap<String,Integer>> cookWorkloading = statisticManager.getDateNameWorkingTime();
        for (String date: cookWorkloading.keySet()){
            ConsoleHelper.writeMessage(date);
            Map<String, Integer> workingTimeByCook = cookWorkloading.get(date);
            for (String cook: workingTimeByCook.keySet()){
                int workingTime = workingTimeByCook.get(cook);
                ConsoleHelper.writeMessage(String.format("%s - %s min", cook, workingTime/60));
            }
            ConsoleHelper.writeMessage("");
        }
    }
    public void printActiveVideoSet(){
        for (Advertisement ad : StatisticAdvertisementManager.getInstance().getActiveVideoSet()) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet(){
        for (Advertisement ad : StatisticAdvertisementManager.getInstance().getArchiveVideoSet()) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}
