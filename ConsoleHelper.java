package com.javarush.task.task27.task2712;

/*
1. Мы много работаем с консолью. Пора создать единую точку взаимодействия.
Создай класс ConsoleHelper с единственным BufferedReader, через который будем работать с консолью.
Запомни, этот класс не хранит никаких данных и состояний, поэтому все методы будут статическими.
Создай в нем три метода:
- writeMessage(String message) - для вывода message в консоль
- String readString() - для чтения строки с консоли
- List<Dish> getAllDishesForOrder() - просит пользователя выбрать блюдо и добавляет его в список.
Выведи список всех блюд и попроси пользователя ввести строку - название блюда.
Введенное 'exit' означает завершение заказа.
В случае, если введенное блюдо не представлено в меню, выведи сообщение о том, что такого блюда нет и
продолжи формирование заказа.
Исключения ввода-вывода бросай выше, на этом уровне не понятно, что с ними делать.

2. Сделай рефакторинг - работа с консолью должна быть только через класс ConsoleHelper.
 */

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List <Dish> dishes=new ArrayList<>();
        writeMessage(Dish.allDishesToString());
        while (true) {
            String dishToOrder=bufferedReader.readLine();
            if ("exit".equalsIgnoreCase(dishToOrder)) break;
            try {
                dishes.add(Dish.valueOf(dishToOrder));
            } catch (IllegalArgumentException e) {
                writeMessage(dishToOrder + " is not detected!");
            }
        }
        return dishes;
    }
}
