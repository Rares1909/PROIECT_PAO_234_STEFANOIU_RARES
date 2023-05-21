package model;

import Util.BurgerIngredients;
import Util.PastaIngredients;
import Util.PizzaToppings;
import Util.Sauce;

import java.util.ArrayList;
import java.util.List;

public abstract class Food {
    protected String name;
    protected int price;
    protected int grams;

    public Food(String name, int price, int grams) {
        this.price = price;
        this.grams = grams;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }


    @Override
    public String toString() {
        return name +
                " {price=" + price +
                ", grams=" + grams
                ;
    }

    public static Food ToFood(String s) {
            String[] food = s.split(" ");
            String type = food[3];
            String name = food[0];
            int price = Integer.valueOf(food[1]);
            int grams = Integer.valueOf(food[2]);
            List<PizzaToppings> pizzaToppings = new ArrayList<>();
            List<BurgerIngredients> burgerIngredients = new ArrayList<>();
            List<PastaIngredients> pastaIngredients = new ArrayList<>();
            if (type.contentEquals("Pizza")) {
                for (int i = 4; i < food.length; i++)
                    pizzaToppings.add(PizzaToppings.valueOf(food[i]));
                return (new Pizza(name, price, grams, pizzaToppings));
            }
            if (type.contentEquals("Burger")) {
                for (int i = 4; i < food.length; i++)
                    burgerIngredients.add(BurgerIngredients.valueOf(food[i]));
                return (new Burger(name, price, grams, burgerIngredients));
            }
            if (type.contentEquals("Pasta")) {
                Sauce sauce = Sauce.valueOf(food[4]);
                for (int i = 5; i < food.length; i++)
                    pastaIngredients.add(PastaIngredients.valueOf(food[i]));
                return (new Pasta(name, price, grams, sauce, pastaIngredients));}
        return null;
    }

    public static String ToFile(Food f){
        String s="";
        s += f.getName()+ ' ' + f.getPrice() + ' ' + f.getGrams();
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
        return s;
    }
}
