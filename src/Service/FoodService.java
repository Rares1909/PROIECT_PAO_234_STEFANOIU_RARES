package Service;

import model.Food;

import java.util.List;

public interface FoodService {
    void addItem(Food food);

    List<Food> getItems();
}
