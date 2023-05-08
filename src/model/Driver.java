package model;

import Service.Impl.MainServiceImpl;
import Service.MainService;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Driver extends Employee {
    private String car;

    private Queue<Order> orders;
    public Driver(){
        super();
        this.orders = new LinkedList<>();
    }

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Driver(String name, int salary, Date birthdate, int experience, String car,Queue orders) {
        super(name, salary, birthdate, experience);
        this.car = car;
        this.orders = orders;
        if (orders == null)
            this.orders = new LinkedList<>();
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

    public void addOrder(Order o) throws IOException {
        service.Audit("Add order to driver");
        this.orders.add(o);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", orders=" + orders+
                ", car='" + car + '\'' +
                '}';
    }
}
