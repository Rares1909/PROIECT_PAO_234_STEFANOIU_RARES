package model;

import Service.EmployeesService;
import Service.Impl.EmployeeServiceImpl;
import Service.Impl.MainServiceImpl;
import Service.Impl.OrderServiceImpl;
import Service.MainService;
import Service.OrderInterface;
import Util.Constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Order {
    private static OrderInterface orders;

    static {
        try {
            orders = OrderServiceImpl.getInstance();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int count=0;
    private int id;
    private Restaurant restaurant;
    private List<Food> food;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Order(Restaurant restaurant,List<Food> food) {
        this.restaurant = restaurant;
        this.food = food;
        this.id=count;
        count+=1;
    }

    public Order() {
        id=count;
        count+=1;
    }

    public int getId() {
        return id;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

   public int calculate_price() throws IOException {
       int sum=0;
       for(Food f:food){
           sum+=f.getPrice();
       }
       service.Audit("Calculate order price");
       return sum+Constants.delivery_fee;

    }

    public Employee get_driver() throws IOException {
        service.Audit("get driver");
        EmployeesService employees= EmployeeServiceImpl.getInstance();
        for(Employee e: employees.getEmployees()){
                if(e instanceof Driver)
                    if ( ((Driver) e).getOrders().contains(this))
                        return e;

        }
        return null;

    }

    @Override
    public String toString() {
        return "Order{" +
                "restaurant=" + restaurant +
                ", food=" + food +
                '}';
    }
}
