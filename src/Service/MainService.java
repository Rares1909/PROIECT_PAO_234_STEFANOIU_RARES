package Service;

import java.io.IOException;

public interface MainService {
    void AddRestaurant() throws IOException;
    void PlaceOrder() throws Exception;

    void Audit(String s) throws IOException;

    void Write() throws IOException;
}
