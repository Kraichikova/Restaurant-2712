package com.javarush.task.task27.task2712.ad;

public class Advertisement {
    private Object content; //- видео
    private String name; // - имя/название
    private long initialAmount; // - начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits; // - количество оплаченных показов
    private int duration; // - продолжительность в секундах

    long amountPerOneDisplaying;


    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        this.amountPerOneDisplaying = hits == 0 ? 0 : initialAmount / hits;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getHits() {
        return hits;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }
    /*
    2.5. В классе Advertisement создайте публичный метод void revalidate(). Этот метод должен:
    2.5.1. Бросать UnsupportedOperationException, если количество показов не положительное число.
    2.5.2. Уменьшать количество показов.
    */
    public void revalidate() {
        if (hits<=0) throw new UnsupportedOperationException();
        hits--;
    }


}
