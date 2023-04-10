package Service.Impl;

import Service.FoodService;
import model.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService {
    private static List<Food>items=new ArrayList<Food>();
    @Override
    public void addItem(Food food) {
        items.add(food);
    }

    @Override
    public List<Food> getItems() {
        return items;
    }
}
