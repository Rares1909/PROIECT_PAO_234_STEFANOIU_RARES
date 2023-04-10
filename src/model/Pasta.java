package model;
import Util.Sauce;
import Util.PastaIngredients;

import java.util.List;

public class Pasta extends  Food  {
    private Sauce sauce;
    private List<PastaIngredients> extratoppings;

    public Pasta(String name, int price, int grams, Sauce sauce, List<PastaIngredients> extratoppings) {
        super(name, price, grams);
        this.sauce = sauce;
        this.extratoppings = extratoppings;
    }


    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public List<PastaIngredients> getExtratoppings() {
        return extratoppings;
    }

    public void setExtratoppings(List<PastaIngredients> extratoppings) {
        this.extratoppings = extratoppings;
    }

    @Override
    public String toString() {
        return super.toString()+ " ,sauce: "+sauce+", ingredients: "+ extratoppings+"}";
    }
}
