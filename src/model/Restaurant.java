package model;

import Service.Impl.MainServiceImpl;
import Service.Impl.RestaurantServiceImpl;
import Service.MainService;
import Service.RestaurantService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Restaurant {
    private static RestaurantService restaurants;

    static {
        try {
            restaurants = RestaurantServiceImpl.getInstance();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int count=0;
    private int id;
    private String name;
    private String address;

    private SortedSet<Food> Menu;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Restaurant(String name, String address, SortedSet<Food> menu) throws IOException {
        this.name = name;
        this.address = address;
        this.Menu = menu;
        this.id=count;
        if(menu==null)
            this.Menu = new TreeSet<>(new Comparator<Food>() {
                public int compare(Food food1, Food food2) {
                    return Double.compare(food1.getPrice(), food2.getPrice());
                }
            });
        count+=1;
        restaurants.addRestaurant(this);
    }

    public Restaurant() throws IOException {

        this.Menu = new TreeSet<>(new Comparator<Food>() {
            public int compare(Food food1, Food food2) {
                return Double.compare(food1.getPrice(), food2.getPrice());
            }
        });
        restaurants.addRestaurant(this);
        id=count;
        count+=1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<Food> getMenu() {
        return Menu;
    }

    public void setMenu(SortedSet<Food> menu) {
        Menu = menu;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public void addFood(Food food) throws IOException {
        service.Audit("Update menu");
        this.Menu.add(food);
    }

    public Food getItem(int nr){
        int i=0;
        for(Food f: Menu){
            if(i==nr) {
                return f;
            }
            i++;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
