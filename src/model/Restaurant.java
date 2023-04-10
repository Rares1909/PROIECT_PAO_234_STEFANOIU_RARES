package model;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Restaurant {
    private String name;
    private String address;

    private SortedSet<Food> Menu;

    public Restaurant(String name, String address, SortedSet<Food> menu) {
        this.name = name;
        this.address = address;
        this.Menu = menu;
        if(menu==null)
            this.Menu = new TreeSet<>(new Comparator<Food>() {
                public int compare(Food food1, Food food2) {
                    return Double.compare(food1.getPrice(), food2.getPrice());
                }
            });
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

    public void addFood(Food food){
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
