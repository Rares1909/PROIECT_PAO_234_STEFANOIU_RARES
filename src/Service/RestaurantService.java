package Service;

import model.Food;
import model.Restaurant;

import java.util.List;

public interface RestaurantService {
    void addRestaurant(Restaurant restaurant);
    List<Restaurant> getRestaurants() throws  Exception;
}
