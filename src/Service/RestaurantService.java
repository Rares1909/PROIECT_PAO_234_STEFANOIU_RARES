package Service;

import model.Food;
import model.Restaurant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant restaurant) throws IOException;
    List<Restaurant> getRestaurants() throws  Exception;
    void read(Restaurant r) throws IOException;
    void write() throws IOException;
    void load() throws IOException;
}
