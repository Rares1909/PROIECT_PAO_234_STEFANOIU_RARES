package Service;

import model.Order;

import java.util.List;

public interface OrderInterface {
    void addOrder(Order order);

    List<Order> getallOrders();
}
