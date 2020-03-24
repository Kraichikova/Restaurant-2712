package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestOrder extends Order {
         public TestOrder(Tablet tablet) throws IOException  {
            super(tablet);
        }

        @Override
        protected void initDishes() {
            List<Dish> allDishes = Arrays.asList(Dish.values());
            Collections.shuffle(allDishes);
            int numSelectedDishes = ThreadLocalRandom.current().nextInt(allDishes.size()) + 1;
            dishes = new ArrayList<>();
            for (int i = 0; i < numSelectedDishes; i++) {
                dishes.add(allDishes.get(i));
            }
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }

        @Override
        public int getTotalCookingTime() {
            return super.getTotalCookingTime();
        }

        @Override
        public String toString() {
            return super.toString();
        }
}
