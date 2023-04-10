package Service.Impl;

import Service.RestaurantService;
import model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {
    private static List<Restaurant>restaurants=new ArrayList<Restaurant>();
    @Override
    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurants()  {
        return restaurants;

    }
}
