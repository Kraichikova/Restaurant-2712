package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jdk.nashorn.internal.objects.NativeRegExp.test;


/*
5. У нас все завязано на работу с консолью. Однако, при возникновении исключений, наше приложение умрет.
Чтобы узнать причину - добавим в Tablet статическое поле logger типа java.util.logging.Logger,
инициализированное именем класса (Logger.getLogger(Tablet.class.getName())).

 */
public class Tablet {
    private final int number; //номер планшета, чтобы можно было однозначно установить, откуда поступил заказ. Номер планшета должен инициализироваться в конструкторе переданным параметром.
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public Tablet(int number) {
        this.number = number;
    }

    /* будет создавать заказ из тех блюд, которые выберет пользователь.
        6. В методе createOrder класса Tablet обработаем исключения ввода-вывода.
    Запишем в лог "Console is unavailable.". Уровень лога - SEVERE - это самый серьезный уровень,
    мы не можем работать.
    Также в методе createOrder класса Tablet должен быть создан новый заказ.
    */
    public Order createOrder()  {
        Order order = null;
        try {
            order = new Order(this);
            if (!order.isEmpty()) {
    //            setChanged();
    //            notifyObservers(order);
                queue.add(order);
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        return order;
    }

    public void createTestOrder() {
        TestOrder order = null;
        try {
            order = new TestOrder(this);
            if (!order.isEmpty()) {
      //          setChanged();
      //          notifyObservers(order);
                queue.add(order);
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }


    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
