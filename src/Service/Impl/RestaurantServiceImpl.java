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
       int j=2;
       while(j< rline.length){
           String [] food=rline[j].split(" ");
           String type=food[3];
           String name=food[0];
           int price=Integer.valueOf(food[1]);
           int grams=Integer.valueOf(food[2]);
           List<PizzaToppings> pizzaToppings=new ArrayList<>();
           List<BurgerIngredients> burgerIngredients=new ArrayList<>();
           List<PastaIngredients> pastaIngredients=new ArrayList<>();
           if(type.contentEquals("Pizza")){
               for(int i=4;i< food.length;i++)
                   pizzaToppings.add(PizzaToppings.valueOf(food[i]));
               r.addFood(new Pizza(name,price,grams,pizzaToppings));}
           if(type.contentEquals("Burger")){
               for(int i=4;i< food.length;i++)
                   burgerIngredients.add(BurgerIngredients.valueOf(food[i]));
               r.addFood(new Burger(name,price,grams,burgerIngredients));}
           if(type.contentEquals("Pasta")) {
               Sauce sauce=Sauce.valueOf(food[4]);
               for(int i=5;i< food.length;i++)
                   pastaIngredients.add(PastaIngredients.valueOf(food[i]));
               r.addFood(new Pasta(name,price,grams,sauce,pastaIngredients));

           }
           j+=1;
       }
       }


    @Override
    public void write() throws IOException {
        FileWriter fw = new FileWriter("src\\Util\\Restaurants.csv",false);
        RestaurantService orders=RestaurantServiceImpl.getInstance();
        String s="";
        for(Restaurant r:restaurants){
            s=r.getName()+','+r.getaddress();
            for(Food f:r.getMenu())
            {
                s += ',' + f.getName()+ ' ' + f.getPrice() + ' ' + f.getGrams();
                if(f instanceof Pizza)
                {s+=' '+"Pizza";
                    for(int j=0;j<((Pizza) f).getToppings().toArray().length;j++)
                        s+=' '+((Pizza) f).getToppings().get(j).toString();}
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
    public void load() throws IOException {
        rsc.reset();
        rsc.useDelimiter("\n");
        while(rsc.hasNext()){
            Restaurant r=new Restaurant();
            read(r);
        }

    }
}
