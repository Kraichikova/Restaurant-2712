package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private final static int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> QUEUE = new LinkedBlockingQueue<>();
    private final static LinkedBlockingQueue<Order> READY_ORDER_QUEUE = new LinkedBlockingQueue<>();

    /*
    3. Пишем main.
    Для объекта Observable добавляем свой объект Observer. См. п.2 и описание паттерна в wikipedia
    Называем повара, имя не влияет на тесты. В моем варианте - это Amigo : )

    Сверим выводы в консоль. Пример моего вывода:
    Your order: [Soup] of Tablet{number=5}
    Start cooking - Your order: [Soup] of Tablet{number=5}

        Для объекта Observable добавляем свой объект Observer. См. п.2 и описание паттерна в wikipedia
    Называем повара, имя не влияет на тесты. В моем варианте - это Amigo :)
    Сверим выводы в консоль. Пример моего вывода:
    Your order: [Water] of Tablet{number=5}
    Start cooking - Your order: [Water] of Tablet{number=5}
    Your order: [Water] of Tablet{number=5} was cooked by Amigo
     */
    public static void main(String[] args) {
        Waiter waiter1 = new Waiter();
        waiter1.setReadyOrderQueue(READY_ORDER_QUEUE);
        waiter1.setQueue(QUEUE);
        List<Tablet> tablets = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(QUEUE);

            tablets.add(tablet);
        }
        Cook cook1 = new Cook("Amigo");
        cook1.setQueue(QUEUE);
        cook1.setReadyOrderQueue(READY_ORDER_QUEUE);
        Cook cook2 = new Cook("Alex");
        cook2.setQueue(QUEUE);
        cook2.setReadyOrderQueue(READY_ORDER_QUEUE);
        StatisticManager.getInstance().register(cook1);
        StatisticManager.getInstance().register(cook2);

        Thread createOrderThread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        Thread cook1Thread = new Thread(cook1);
        Thread cook2Thread = new Thread(cook2);
        Thread waiter1Thread = new Thread(waiter1);

        createOrderThread.start();
        cook1Thread.start();
        cook2Thread.start();
        waiter1Thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        cook1Thread.interrupt();
        cook2Thread.interrupt();
        createOrderThread.interrupt();
        waiter1Thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printActiveVideoSet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printArchivedVideoSet();
        directorTablet.printCookWorkloading();

    }
}
