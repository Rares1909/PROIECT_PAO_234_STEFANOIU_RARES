package Service.Impl;

import Service.OrderInterface;
import Util.Constants;
import exception.InvalidOrder;
import model.Order;
import static validation.OrderValidation.Min_Price;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderInterface {
    private static List<Order> orders=new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        if(Min_Price(order)==false)
            throw new InvalidOrder("Minimum price of "+ Constants.min_price+" not achieved.");
        orders.add(order);

    }

    @Override
    public List<Order> getallOrders() {
        return orders;
    }
}
