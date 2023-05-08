package Service;

import model.Order;

import java.io.IOException;
import java.util.List;

public interface OrderInterface {
    void addOrder(Order order) throws IOException;

    List<Order> getallOrders() throws IOException;


    void read(Order o) throws Exception;

    void write() throws IOException;

    void load() throws Exception;
}
