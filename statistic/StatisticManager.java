package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

/*
Хранилище связано 1 к 1 с менеджером, т.е. один менеджер и одно хранилище на приложение.
К хранилищу может доступиться только StatisticManager. Поэтому...
Из вышеперечисленного следует, что хранилище должно быть приватным иннер классом.
Назовем его StatisticStorage.

1. Внутри класса StatisticManager создать приватный иннер класс StatisticStorage.

2. Чтобы менеджер мог получить доступ к хранилищу, нужно в классе StatisticManager
создать поле statisticStorage типа StatisticStorage.
Инициализируй его экземпляром класса.

3. StatisticStorage будет хранить данные внутри себя в виде мапы/словаря storage.
Связь StatisticStorage и Map должна быть has-a
Типы для мапы - <EventType, List<EventDataRow>>

4. В конструкторе StatisticStorage инициализируй хранилище данными по-умолчанию:
например используя цикл, для каждого EventType добавь new ArrayList<EventDataRow>()
 */
public class StatisticManager {  //singleton
    private static StatisticManager statisticManager;
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();


    private StatisticManager() {
    }

    public static StatisticManager getInstance() {
        if (statisticManager == null) {
            statisticManager = new StatisticManager();
        }
        return statisticManager;
    }

    private static class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }
        private void put(EventDataRow data){
            storage.get(data.getType()).add(data);
        }
        private List<EventDataRow> getDataByType(EventType type){
            return storage.get(type);
        }
    }

    public void register(EventDataRow data) {   // будет регистрировать событие в хранилище.
        if (data != null) statisticStorage.put(data);
    }


    public void register(Cook cook){// зарегистрирует полученного повара.
      cooks.add(cook);
    }

    public Set<Cook> getCooks() {
        return cooks;
    }

    /*
    2. В StatisticManager создай метод (придумать самостоятельно),
     который из хранилища достанет все данные, относящиеся к отображению рекламы,
     и посчитает общую прибыль за каждый день.
    Дополнительно добавь вспомогательный метод get в класс хранилища, чтобы получить доступ к данным.
    */
    public TreeMap<String, Long> getAdsRevenueByDate(){
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        TreeMap<String, Long> result = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                try
                {
                    Date date1 = format.parse(o1);
                    Date date2 = format.parse(o2);
                    if (date1.equals(date2)) return 0;
                    return date1.after(date2)?-1:1;
                } catch (Exception e){
                    ConsoleHelper.writeMessage("incorrect date was written, check StatisticManager:getAdsRevenueByDate");
                }
                return 0;
            }
        });
        List<VideoSelectedEventDataRow> shownAdsList = (List<VideoSelectedEventDataRow>)(List<?>) statisticStorage.getDataByType(EventType.SELECTED_VIDEOS);
        for (VideoSelectedEventDataRow videoData : shownAdsList){
            long revenue = videoData.getAmount();
            String date = format.format(videoData.getDate());
            if (result.containsKey(date))
                result.put(date, result.get(date) + revenue);
            else
                result.put(date, revenue);
        }
        return result;
    }

    /*
    4.Реализуем второй пункт статистики - загрузка (рабочее время) повара, сгруппировать по дням.
      В StatisticManager создай метод (придумать самостоятельно),
      который из хранилища достанет все данные, относящиеся к работе повара,
      и посчитает общую продолжительность работы для каждого повара отдельно.
     */
    public TreeMap<String, TreeMap<String,Integer>> getDateNameWorkingTime(){
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        TreeMap<String, TreeMap<String,Integer>> result = new TreeMap<>(new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                try
                {
                    if (o1.equals(o2)) return 0;
                    Date date1 = format.parse(o1);
                    Date date2 = format.parse(o2);
                    return date1.after(date2)?-1:1;
                } catch (Exception e){
                    ConsoleHelper.writeMessage("incorrect date was written, check StatisticManager:getDateNameWorkingTime");
                }
                return 0;
            }
        });
        List<CookedOrderEventDataRow> cookedOrders = (List<CookedOrderEventDataRow>)(List<?>) statisticStorage.getDataByType(EventType.COOKED_ORDER);
        for (CookedOrderEventDataRow cookedOrderData : cookedOrders){
            String date = format.format(cookedOrderData.getDate());
            String cook = cookedOrderData.getCookName();
            Integer time = cookedOrderData.getTime();
            if (result.containsKey(date)){
                TreeMap<String, Integer> workingTimeByCook = result.get(date);
                if (workingTimeByCook.containsKey(cook))
                    workingTimeByCook.put(cook, workingTimeByCook.get(cook)+time);
                else
                    workingTimeByCook.put(cook, time);
            }
            else {
                TreeMap<String, Integer> workingTimeByCook = new TreeMap<>();
                result.put(date, workingTimeByCook);
                workingTimeByCook.put(cook, time);
            }
        }
        return result;
    }
}
