import Service.*;
import Service.Databases.EmployeeDatabase;
import Service.Databases.OrderDatabase;
import Service.Databases.RestaurantDatabase;
import Service.Impl.*;
import Util.*;
import config.DatabaseConfiguration;
import model.*;

import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //clasele service
        Scanner scan= new Scanner(System.in);

        FoodService food= FoodServiceImpl.getInstance();
        RestaurantService restaurants= RestaurantServiceImpl.getInstance();
        EmployeesService employees= EmployeeServiceImpl.getInstance();
        OrderInterface orders= OrderServiceImpl.getInstance();
        MainService main= MainServiceImpl.getInstance();

       DatabaseConfiguration databaseConfiguration=new DatabaseConfiguration();
        RestaurantDatabase restaurantDatabase=new RestaurantDatabase(databaseConfiguration);
        EmployeeDatabase employeeDatabase=new EmployeeDatabase(databaseConfiguration);
        OrderDatabase orderDatabase=new OrderDatabase(databaseConfiguration);




       /*restaurants.load();
        orders.load();
        employees.load();*/


        restaurantDatabase.read();
        orderDatabase.read();
        employeeDatabase.read_driver();
        employeeDatabase.read_cook();

        for(Restaurant r: restaurants.getRestaurants())
            restaurantDatabase.delete(r);
        for(Order o: orders.getallOrders())
            orderDatabase.delete(o);
        for(Employee e: employees.getEmployees())
            employeeDatabase.delete(e);


        for(Restaurant r: restaurants.getRestaurants())
            restaurantDatabase.create(r);
        for(Order o: orders.getallOrders())
            orderDatabase.create(o);
        for(Employee e: employees.getEmployees())
            employeeDatabase.create(e);


        food.addItem(new Pizza("Pepperoni",8,400, Arrays.asList(PizzaToppings.Pepperoni,PizzaToppings.Cheese)));
        food.addItem(new Pasta("Bolognese",7,300, Sauce.Bolognese,Arrays.asList(PastaIngredients.Parmesan)));
        food.addItem(new Burger("Cheeseburger",9,450,Arrays.asList(BurgerIngredients.cheese,BurgerIngredients.pickles
                ,BurgerIngredients.onions)));






        employees.addEmployee(new Driver("Marius",2000, new Date(99,9,19),
                1,"Dacia",null));       //not enough experience
        employees.addEmployee(new Driver("Radu",2000, new Date(118,9,19),
                3,"Dacia",null));       //invalid birth date

        /*employees.addEmployee(new Driver("Marius",2000, new Date(99,9,19),
                3,"Dacia",null));
        employees.addEmployee(new Driver("Radu",2000, new Date(99,9,19),
                3,"Dacia",null));
        employees.addEmployee(new Driver("Mihai",2000, new Date(99,9,19),
                3,"Dacia",null));*/
       /* Cook c1=(new Cook("Andrei",3000,new Date(90,10,12),5, Rank.Intermediate,restaurants.getRestaurants().get(0)));
        c1.IncreaseRank();*/


        //System.out.println(employees.getDrivers());
        System.out.println(employees.getEmployees());
        System.out.println();
        System.out.println("#################################\n");

        System.out.println("Hello mister manager. Add another restaurant");
        main.AddRestaurant();

        System.out.println("Hello Customer! Please make an order");
        main.PlaceOrder();

        for(Restaurant r: restaurants.getRestaurants())
            restaurantDatabase.create(r);
        for(Order o: orders.getallOrders())
            orderDatabase.create(o);
        for(Employee e: employees.getEmployees())
            employeeDatabase.create(e);

        for(Employee e: employees.getEmployees())
            employeeDatabase.update(e);

        System.out.println();
        System.out.println("#################################\n");

        System.out.println("Update first restaurant's name: ");
        Restaurant r=restaurants.getRestaurants().get(0);
        r.setName(scan.next());
        restaurantDatabase.update(r);

        System.out.println("Adding food to the first order...");
        Order o=orders.getallOrders().get(0);
        o.addFood(food.getItems().get(0));
        orderDatabase.update(o);


        employees.write();
        orders.write();
        restaurants.write();

        main.Write();

    }
}