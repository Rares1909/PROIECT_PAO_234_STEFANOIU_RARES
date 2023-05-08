package Service.Impl;

import Service.MainService;
import Service.OrderInterface;
import Service.RestaurantService;
import Util.*;
import exception.InvalidOrder;
import model.*;

import static validation.OrderValidation.Min_Price;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderServiceImpl implements OrderInterface {
    private static OrderServiceImpl instance =null;

    private static Scanner osc;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  OrderServiceImpl() throws FileNotFoundException {
        osc = new Scanner(new File("src\\Util\\Orders.csv"));
        osc.useDelimiter("\n");
    }

    public static OrderServiceImpl getInstance() throws FileNotFoundException {
        if (instance==null)
            instance=new OrderServiceImpl();
        return instance;
    }

    private static List<Order> orders=new ArrayList<>();

    @Override
    public void addOrder(Order order) throws IOException {
        if(Min_Price(order)==false)
            throw new InvalidOrder("Minimum price of "+ Constants.min_price+" not achieved.");
        orders.add(order);
        service.Audit("Add order");

    }

    @Override
    public List<Order> getallOrders() throws IOException {
        service.Audit("Get orders");
        return orders;
    }

    @Override
    public void read(Order o) throws Exception {
        if(!osc.hasNext())
            return;
        String line=osc.next();
        String [] oline=line.split(",");
        RestaurantService restaurants=RestaurantServiceImpl.getInstance();
        o.setRestaurant(restaurants.getRestaurants().get(Integer.valueOf(oline[0])));
        List<Food>items=new ArrayList<>();
        int j=1;
        while(j<oline.length){
            String [] food=oline[j].split(" ");     //fiecare fel de mancare este separat prin virgula,iar atributele lui prin spatiu
            String type=food[3];
            String name=food[0];
            int price=Integer.valueOf(food[1]);
            int grams=Integer.valueOf(food[2]);
            List<PizzaToppings> pizzaToppings=new ArrayList<>();
            List<BurgerIngredients> burgerIngredients=new ArrayList<>();
            List<PastaIngredients> pastaIngredients=new ArrayList<>();
                if(type.contentEquals("Pizza")){        //transformam din string in tipul dorit de ingredient
                    for(int i=4;i< food.length;i++)
                        pizzaToppings.add(PizzaToppings.valueOf(food[i]));
                items.add(new Pizza(name,price,grams,pizzaToppings));}
                if(type.contentEquals("Burger")){
                    for(int i=4;i< food.length;i++)
                        burgerIngredients.add(BurgerIngredients.valueOf(food[i]));
                items.add(new Burger(name,price,grams,burgerIngredients));}
                if(type.contentEquals("Pasta")) {
                    Sauce sauce=Sauce.valueOf(food[4]);
                    for(int i=5;i< food.length;i++)
                        pastaIngredients.add(PastaIngredients.valueOf(food[i]));
                    items.add(new Pasta(name,price,grams,sauce,pastaIngredients));

                }
                j+=1;
        }
        o.setFood(items);
        addOrder(o);
    }

    @Override
    public void write() throws IOException {
        FileWriter fw = new FileWriter("src\\Util\\Orders.csv",false);
        OrderInterface orders=OrderServiceImpl.getInstance();
        String s="";
        for(Order o:orders.getallOrders()){
            s=Integer.toString(o.getRestaurant().getId());
            for(int i=0;i<o.getFood().toArray().length;i++) {
                s += ',' + o.getFood().get(i).getName() + ' ' + o.getFood().get(i).getPrice() + ' ' + o.getFood().get(i).getGrams();
                Food f=o.getFood().get(i);
                if(f instanceof Pizza)
                {s+=' '+"Pizza";        //scriem si tipul pentru a stii in ce transformam ingredientele la citire
                    for(int j=0;j<((Pizza) f).getToppings().toArray().length;j++)
                        s+=' '+((Pizza) f).getToppings().get(j).toString();}  //transformam in string
                if(f instanceof Burger)
                {s+=' '+"Burger";
                    for(int j=0;j<((Burger) f).getIngredients().toArray().length;j++)
                        s+=' '+((Burger) f).getIngredients().get(j).toString();}
                if(f instanceof Pasta){
                    s+=' '+"Pasta";
                    s+=' '+((Pasta) f).getSauce().toString();
                    for(int j=0;j<((Pasta) f).getExtratoppings().toArray().length;j++)
                        s+=' '+((Pasta) f).getExtratoppings().get(j).toString();}
            }
            fw.write(s);
            fw.write("\n");
        }
        fw.close();
    }

    @Override
    public void load() throws Exception {
        osc.reset();
        osc.useDelimiter("\n");
        while(osc.hasNext())
        {Order o=new Order();
         read(o);}

    }
}
