package validation;

import Util.Constants;
import model.Order;

public class OrderValidation {
    public static boolean Min_Price(Order order){
        if(order.calculate_price()-Constants.delivery_fee< Constants.min_price)
            return false;
        return true;            //pret minim fara livrare 10 euro
    }
}
