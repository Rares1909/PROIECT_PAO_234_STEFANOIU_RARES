package Service.Impl;

import Service.MainService;
import Service.OrderInterface;
import Service.RestaurantService;
import Util.BurgerIngredients;
import Util.PastaIngredients;
import Util.PizzaToppings;
import Util.Sauce;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RestaurantServiceImpl implements RestaurantService {
    private static RestaurantServiceImpl instance=null;

    private Scanner rsc;
    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private RestaurantServiceImpl() throws FileNotFoundException {
        rsc = new Scanner(new File("src\\Util\\Restaurants.csv"));
        rsc.useDelimiter("\n");
    }

    public static RestaurantServiceImpl getInstance() throws FileNotFoundException {
        if (instance==null)
            instance=new RestaurantServiceImpl();
        return instance;
    }
    private static List<Restaurant>restaurants=new ArrayList<Restaurant>();
    @Override
    public void addRestaurant(Restaurant restaurant) throws IOException {
        restaurants.add(restaurant);
        service.Audit("Add restaurant");
    }

    @Override
    public List<Restaurant> getRestaurants() throws IOException {
        service.Audit("Get restaurants");
        return restaurants;

    }

    @Override
    public void read(Restaurant r) throws IOException {
        RestaurantService restaurants=RestaurantServiceImpl.getInstance();
       if(!rsc.hasNext())
       {return;}
       String line= rsc.next();
       String [] rline=line.split(",");
       r.setName(rline[0]);
       r.setaddress(rline[1]);
       String [] fv=rline[2].split(";");
       int j=0;
       while(j< fv.length){
           r.addFood(Food.ToFood(fv[j]));
           j+=1;}

       }


    @Override
    public void write() throws IOException {
        FileWriter fw = new FileWriter("src\\Util\\Restaurants.csv",false);
        RestaurantService orders=RestaurantServiceImpl.getInstance();
        String s="";
        for(Restaurant r:restaurants){
            int ok=1;
            s=r.getName()+','+r.getaddress()+',';
            for(Food f:r.getMenu())
            {
                if (ok==1)
                    ok=0;
                else
                    s+=';';
                s+=Food.ToFile(f);
            }
            fw.write(s);
            fw.write("\n");
        }
        fw.close();

    }

    @Override
    public void load() throws IOException {
        rsc.reset();
        rsc.useDelimiter("\n");
        while(rsc.hasNext()){
            Restaurant r=new Restaurant();
            read(r);
        }

    }
}
