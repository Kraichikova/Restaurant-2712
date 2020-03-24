package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

import static com.javarush.task.task27.task2712.ConsoleHelper.getAllDishesForOrder;
import static com.javarush.task.task27.task2712.ConsoleHelper.writeMessage;

/*
4. В классе Order (заказ) должна быть информация, относящаяся к списку выбранных пользователем блюд.
Т.е. где-то должен быть список всех блюд, и должен быть список выбранных блюд в классе Order.
В классе Order нужны поля private final Tablet tablet и protected List<Dish> dishes.
Конструктор должен принимать один параметр типа Tablet и инициализировать поле tablet.
 */

public class Order {
    protected List<Dish> dishes;
    private final Tablet tablet;

    public Tablet getTablet() {
        return tablet;
    }

    public Order(Tablet tablet) throws IOException{
        this.tablet = tablet;
   //     dishes= ConsoleHelper.getAllDishesForOrder();
        initDishes();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    protected void initDishes() throws IOException {
        dishes= ConsoleHelper.getAllDishesForOrder();
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public int getTotalCookingTime() { //in minutes
        int time = 0;
        for (Dish dish : dishes) {
            time += dish.getDuration();
        }
        return time;
    }

    /*
    4. Перепиши метод toString в классе Order. Пусть он возвращает пустую строку,
     если нет блюд в заказе, иначе вывод должен быть аналогичным примеру в порядке добавления блюд.
     Используй ConsoleHelper.
     Пример:
        Your order: [Juice, Fish] of Tablet{number=5}
     */

    @Override
    public String toString() {
        return dishes.isEmpty() ? "" : "Your order: " + dishes + " of " + tablet;
    }
}
