package model;

public abstract class Food {
    protected String name;
    protected int price;
    protected int grams;

    public Food(String name, int price, int grams) {
        this.price = price;
        this.grams = grams;
        this.name=name;
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
        return    name  +
                " {price=" + price +
                ", grams=" + grams
                ;}

}
