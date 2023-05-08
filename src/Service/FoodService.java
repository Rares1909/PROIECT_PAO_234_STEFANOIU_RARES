package Service;

import model.Food;

import java.io.IOException;
import java.util.List;

public interface FoodService {
    void addItem(Food food) throws IOException;

    List<Food> getItems() throws IOException;


}
