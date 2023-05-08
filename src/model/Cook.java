package model;

import Service.Impl.MainServiceImpl;
import Service.MainService;
import Util.Constants;
import Util.Rank;

import java.io.IOException;
import java.util.Date;

public class Cook extends Employee {
    private Rank rank;

    private Restaurant restaurant;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Cook(){
        super();
    }

    public Cook(String name, int salary, Date birthdate, int experience, Rank rank,Restaurant restaurant) {
        super(name, salary, birthdate, experience);
        this.rank = rank;
        this.restaurant=restaurant;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void IncreaseRank() throws IOException {
        service.Audit("Increase rank");
        if(rank== Rank.Beginner)
            rank=Rank.Intermediate;
        if(rank==Rank.Intermediate)
            rank=Rank.Advanced;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "name=" + name +
                ", restaurant=" + restaurant +
                ", rank='" + rank + '\'' +
                '}';
    }
}
