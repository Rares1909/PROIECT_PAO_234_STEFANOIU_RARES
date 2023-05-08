package validation;

import Service.Impl.MainServiceImpl;
import Service.MainService;
import Util.Constants;
import model.Order;

import java.io.IOException;

public class OrderValidation {
    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean Min_Price(Order order) throws IOException {
        service.Audit("Minimum price check");
        if(order.calculate_price()-Constants.delivery_fee< Constants.min_price)
            return false;
        return true;            //pret minim fara livrare 10 euro
    }
}
