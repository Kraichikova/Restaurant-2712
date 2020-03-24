package com.javarush.task.task27.task2712;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/*
Этот таск должен:
2.1. Хранить список всех планшетов
2.2. Используя Math.random выбирать случайный планшет.
2.3. У RandomOrderGeneratorTask должен быть только один единственный метод.
2.4. Генерировать случайный заказ каждые ORDER_CREATING_INTERVAL миллисекунд для
 планшета из п.2.2. (не печатай стек-трейс)
 */

public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Tablet tablet = tablets.get(ThreadLocalRandom.current().nextInt(tablets.size()));
            tablet.createTestOrder();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
