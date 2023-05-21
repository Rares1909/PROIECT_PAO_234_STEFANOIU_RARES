package model;

import Util.PizzaToppings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pizza extends Food {
    private List<PizzaToppings> toppings;

    public Pizza(String name, int price, int grams, List<PizzaToppings> toppings) {
        super(name, price, grams);
        this.toppings = toppings;
    }


    public List<PizzaToppings> getToppings() {
        return toppings;
    }

    public void setToppings(List<PizzaToppings> toppings) {
        this.toppings = toppings;
    }

    @Override
    public String toString() {
        return super.toString()+ " ,ingredients: "+toppings+"}";
    }

}
