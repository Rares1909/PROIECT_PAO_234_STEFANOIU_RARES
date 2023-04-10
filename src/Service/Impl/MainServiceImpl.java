package Service.Impl;

import Service.*;
import exception.InvalidOrder;
import model.Driver;
import model.Food;
import model.Order;
import model.Restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainServiceImpl implements MainService {
    private FoodService food=new FoodServiceImpl();
    private RestaurantService restaurants=new RestaurantServiceImpl();
    private EmployeesService employees=new EmployeeServiceImpl();
    private OrderInterface orders=new OrderServiceImpl();
    private  Scanner scan= new Scanner(System.in);
    public void AddRestaurant(){
        System.out.println("Name: ");
        String name= scan.nextLine();
        System.out.println("Adress: ");
        String address=scan.nextLine();

        Restaurant r2=new Restaurant(name,address,null);
        restaurants.addRestaurant(r2);

        System.out.println("Please select some items for the menu by selecting the number (start with 0) and enter -1 when you want to stop");
        System.out.println(food.getItems());
        int item=scan.nextInt();
        while (item!=-1){
            r2.addFood(food.getItems().get(item));
            item=scan.nextInt();
        }
    }

    public void PlaceOrder() throws Exception {
        Driver driver= employees.getDrivers().poll();
        System.out.println(restaurants.getRestaurants());
        System.out.println("Select the number (start with 0) of the restaurant");

        int nr= scan.nextInt();
        System.out.println(restaurants.getRestaurants().get(nr).getMenu());
        System.out.println("Select the number of the food (start with 0). Press -1 when you want to stop");

        List<Food> items=new ArrayList<>();
        boolean valid=false;
        Order order;

        while(!valid)
        {   int f= scan.nextInt();
            while(f!=-1) {
            items.add(restaurants.getRestaurants().get(nr).getItem(f));
            f = scan.nextInt();
        }
        try{
            order = new Order(restaurants.getRestaurants().get(nr), driver,items);
            orders.addOrder(order);
            valid=true;
            driver.addOrder(order);
            employees.addEmployee(driver);
            System.out.println("Your order will cost "+order.calculate_price()+". The driver is "+driver.getName());}
        catch(InvalidOrder e){
            System.out.println(e);}
        }
    }
}
