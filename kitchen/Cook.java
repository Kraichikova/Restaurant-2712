package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook  extends Observable implements Runnable {
    private String name;
    boolean busy;
    private LinkedBlockingQueue<Order> queue;
    private LinkedBlockingQueue<Order> readyOrderQueue;

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void setReadyOrderQueue(LinkedBlockingQueue<Order> readyOrderQueue) {
        this.readyOrderQueue = readyOrderQueue;
    }

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    /*
    3. Метод void update(Observable observable, Object arg), который необходимо реализовать, принимает два параметра.
    - observable - объект, который отправил нам значение
    - arg - само значение, в нашем случае - это объект Order
    На данном этапе мы будем лишь имитировать обработку и выведем в консоль "Start cooking - " + order
     */

    public void startCookingOrder(Order order) {
        busy=true;
        ConsoleHelper.writeMessage("Start cooking - "+ order +
                ", cooking time "+((Order) order).getTotalCookingTime()+"min");

        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(), name,
                ((Order)order).getTotalCookingTime() * 60, ((Order) order).getDishes()));
        try {
            Thread.sleep(order.getTotalCookingTime() * 10);
        } catch (InterruptedException e) {
        }
   //     setChanged();   //dish is ready
      //  notifyObservers(order); //call waiter
        readyOrderQueue.add(order);
        busy=false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                startCookingOrder(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (busy && queue.isEmpty()) break;
        }
    }
}
