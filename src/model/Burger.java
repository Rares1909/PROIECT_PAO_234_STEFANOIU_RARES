package model;
import Util.BurgerIngredients;

import java.util.List;
import java.util.Map;

public class Burger extends Food {
    private List<BurgerIngredients>ingredients;


    public Burger(String name, int price, int grams, List<BurgerIngredients> ingredients) {
        super(name, price, grams);
        this.ingredients = ingredients;
    }


    public List<BurgerIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<BurgerIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return super.toString()+ " ,ingredients: "+ingredients+"}";
    }
}
