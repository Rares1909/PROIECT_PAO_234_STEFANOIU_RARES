package Service.Impl;
import java.time.Instant;

import Service.*;
import exception.InvalidOrder;
import model.Driver;
import model.Food;
import model.Order;
import model.Restaurant;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainServiceImpl implements MainService {
    private static MainServiceImpl instance=null;

    private static FileWriter fw;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String out="";

    private FoodService food=FoodServiceImpl.getInstance();
    private RestaurantService restaurants= RestaurantServiceImpl.getInstance();
    private EmployeesService employees=EmployeeServiceImpl.getInstance();
    private OrderInterface orders=OrderServiceImpl.getInstance();
    private  Scanner scan= new Scanner(System.in);

    private MainServiceImpl() throws IOException {
        fw = new FileWriter("src\\Util\\Audit.csv",false);
    }

    public static MainServiceImpl getInstance() throws IOException {
        if(instance==null)
            instance=new MainServiceImpl();
        return instance;
    }

    public void Audit(String s) throws IOException {
        out+=s+ ','+formatter.format(LocalDateTime.now())+"\n";

    }
    public void Write() throws IOException {
        fw.write(out);
        fw.close();
    }
    public void AddRestaurant() throws IOException {
        System.out.println("Name: ");
        String name= scan.nextLine();
        System.out.println("Adress: ");
        String address=scan.nextLine();

        Restaurant r2=new Restaurant(name,address,null);

        System.out.println("Please select some items for the menu by selecting the number (start with 0) and enter -1 when you want to stop");
        System.out.println(food.getItems());
        int item=scan.nextInt();
        while (item!=-1){                           //adaugam mancare in meniu pana cand -1 este introdus
            r2.addFood(food.getItems().get(item));
            item=scan.nextInt();
        }
        Audit("Input restaurant");
    }

    public void PlaceOrder() throws Exception {
        Driver driver= employees.getDrivers().poll();
        employees.getEmployees().remove(driver);//soferul cu cele mai putine comenzi
        System.out.println(restaurants.getRestaurants());
        System.out.println("Select the number (start with 0) of the restaurant");

        int nr= scan.nextInt();
        System.out.println(restaurants.getRestaurants().get(nr).getMenu());
        System.out.println("Select the number of the food (start with 0). Press -1 when you want to stop");

        List<Food> items=new ArrayList<>();
        boolean valid=false;
        Order order;

        while(!valid)
        {   int f= scan.nextInt();                  //adaugam mancare pana este introuds -1
            while(f!=-1) {
            items.add(restaurants.getRestaurants().get(nr).getItem(f));
            f = scan.nextInt();
        }
        try{
            order = new Order(restaurants.getRestaurants().get(nr),items);
            orders.addOrder(order);
            valid=true;                                 //verificam daca pretul minim este atins
            driver.addOrder(order);
            employees.addEmployee(driver);
            System.out.println("Your order will cost "+order.calculate_price()+". The driver is "+driver.getName());}
        catch(InvalidOrder e){
            System.out.println(e);}
        }
        Audit("Place Order");
    }
}
