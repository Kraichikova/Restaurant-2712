package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

//является интерфейсом-маркером, т.к. не содержит методов,
// и по нему мы определяем, является ли переданный объект событием или нет.
public interface EventDataRow {
    public EventType getType();
    public Date getDate();//  вернет дату создания записи
    public int getTime();// вернет время - продолжительность
}
