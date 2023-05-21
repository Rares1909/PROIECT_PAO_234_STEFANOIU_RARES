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
        String [] fv=oline[1].split(";");
        int j=0;
        while(j<fv.length){
                items.add(Food.ToFood(fv[j]));
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
            int ok=1;
            s=Integer.toString(o.getRestaurant().getId())+',';
            for(int i=0;i<o.getFood().toArray().length;i++) {
                if(ok==1)
                    ok=0;
                else
                    s+=';';
                s +=Food.ToFile(o.getFood().get(i));
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
