package model;

import Util.Constants;

import java.util.List;

public class Order {
    private Restaurant restaurant;
    private Driver driver;
    private List<Food> food;

    public Order(Restaurant restaurant, Driver driver, List<Food> food) {
        this.restaurant = restaurant;
        this.driver = driver;
        this.food = food;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

   public int calculate_price(){
       int sum=0;
       for(Food f:food){
           sum+=f.getPrice();
       }
       return sum+Constants.delivery_fee;
    }

    @Override
    public String toString() {
        return "Order{" +
                "restaurant=" + restaurant +
                ", driver=" + driver +
                ", food=" + food +
                '}';
    }
}
