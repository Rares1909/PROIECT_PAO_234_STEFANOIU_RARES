package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class Driver extends Employee {
    private String car;

    private Queue<Order> orders;

    public Driver(String name, int salary, Date birthdate, int experience, String car,Queue orders) {
        super(name, salary, birthdate, experience);
        this.car = car;
        this.orders=orders;
        if(orders==null)
            this.orders=new LinkedList<>();
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public void setOrders(Queue<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order o){
        this.orders.add(o);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", orders=" + orders +
                ", car='" + car + '\'' +
                '}';
    }
}
