package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

public class Waiter implements Runnable {
    private LinkedBlockingQueue<Order> readyOrderQueue;
    private LinkedBlockingQueue<Order> queue;

    public void setReadyOrderQueue(LinkedBlockingQueue<Order> readyOrderQueue) {
        this.readyOrderQueue = readyOrderQueue;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            if (!readyOrderQueue.isEmpty()) {
                try {
                    ConsoleHelper.writeMessage("Заказ готов! Официант несет на стол: " + readyOrderQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
   //         if (readyOrderQueue.isEmpty() && queue.isEmpty()) break;
        }
    }

    // @Override
    // public void update(Observable o, Object arg) {
    //     ConsoleHelper.writeMessage(arg + " was cooked by " + o);
    // }
}
