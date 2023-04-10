import Service.*;
import Service.Impl.*;
import Util.*;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //clasele service
        FoodService food=new FoodServiceImpl();
        RestaurantService restaurants=new RestaurantServiceImpl();
        EmployeesService employees=new EmployeeServiceImpl();
        OrderInterface orders=new OrderServiceImpl();
        MainService main= new MainServiceImpl();

        food.addItem(new Pizza("Pepperoni",8,400, Arrays.asList(PizzaToppings.Pepperoni,PizzaToppings.Cheese)));
        food.addItem(new Pasta("Bolognese",7,300, Sauce.Bolognese,Arrays.asList(PastaIngredients.Parmesan)));
        food.addItem(new Burger("Cheeseburger",9,450,Arrays.asList(BurgerIngredients.cheese,BurgerIngredients.pickles
                ,BurgerIngredients.onions)));


        Restaurant r1=new Restaurant("Italiano","Universitate",null);
        r1.addFood(food.getItems().get(0));
        r1.addFood(food.getItems().get(1));
        restaurants.addRestaurant(r1);



        employees.addEmployee(new Driver("Marius",2000, new Date(99,9,19),
                1,"Dacia",null));       //not enough experience
        employees.addEmployee(new Driver("Radu",2000, new Date(118,9,19),
                3,"Dacia",null));       //invalid birth date

        employees.addEmployee(new Driver("Marius",2000, new Date(99,9,19),
                3,"Dacia",null));
        employees.addEmployee(new Driver("Radu",2000, new Date(99,9,19),
                3,"Dacia",null));
        employees.addEmployee(new Driver("Mihai",2000, new Date(99,9,19),
                3,"Dacia",null));
        Cook c1=(new Cook("Andrei",3000,new Date(90,10,12),5, Rank.Intermediate,r1));
        c1.IncreaseRank();
        employees.addEmployee(c1);


        System.out.println(employees.getEmployees());
        System.out.println();
        System.out.println("#################################\n");

        System.out.println("Hello mister manager. Add another restaurant");
        main.AddRestaurant();

        System.out.println("Hello Customer! Please make an order");
        main.PlaceOrder();


    }
}