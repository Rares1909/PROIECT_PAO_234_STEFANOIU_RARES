package Service.Databases;

import Service.Impl.OrderServiceImpl;
import Service.Impl.RestaurantServiceImpl;
import Service.OrderInterface;
import Service.RestaurantService;
import config.DatabaseConfiguration;
import model.Food;
import model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDatabase {

    private final DatabaseConfiguration databaseConfiguration;

    public OrderDatabase(DatabaseConfiguration databaseCon) {
        databaseConfiguration = databaseCon;
    }

    public void create(Order order) throws SQLException {
        try{
        String query = "INSERT INTO `order` (id, id_restaurant, food) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,order.getId());
        preparedStatement.setInt(2, order.getRestaurant().getId());
        String s = "";
        int ok = 1;
        for (Food f : order.getFood()) {
            if (ok == 1)
                ok = 0;
            else
                s += ';';
            s += Food.ToFile(f);

        }
        preparedStatement.setString(3, s);
        preparedStatement.execute();
        preparedStatement.close();}
        catch(Exception e){

        }
    }

    public void read() throws Exception {
        Statement statement = databaseConfiguration.getConnection().createStatement();
        String query = "select* from `order`";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            Order o = new Order();
            OrderInterface orderInterface= OrderServiceImpl.getInstance();
            RestaurantService restaurants = RestaurantServiceImpl.getInstance();
            o.setRestaurant(restaurants.getRestaurants().get(resultSet.getInt("id_restaurant")));
            String s = resultSet.getString("food");
            String[] fv = s.split(";");
            int j = 0;
            List<Food> items = new ArrayList<>();
            while (j < fv.length) {
                items.add(Food.ToFood(fv[j]));
                j += 1;

            }
            o.setFood(items);
            orderInterface.addOrder(o);


        }
        statement.close();

    }

    public void update(Order order) throws Exception{
        String query = "UPDATE `order` SET id_restaurant = ?, food=? WHERE id = ?";
        PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,order.getRestaurant().getId());
        String s="";
        int ok=1;
        for(Food f:order.getFood()){
            if(ok==1)
                ok=0;
            else
                s+=';';
            s+=Food.ToFile(f);

        }
        preparedStatement.setString(2,s);
        preparedStatement.setInt(3,order.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(Order o) throws SQLException {
        String query = "DELETE FROM `order` WHERE id = ?";
        PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,o.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }
}
