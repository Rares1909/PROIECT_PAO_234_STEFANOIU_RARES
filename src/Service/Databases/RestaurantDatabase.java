package Service.Databases;

import config.DatabaseConfiguration;
import model.Food;
import model.Restaurant;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabase {
    private final DatabaseConfiguration databaseConfiguration;

    public RestaurantDatabase(DatabaseConfiguration databaseCon) {
        databaseConfiguration = databaseCon;
    }
    public void read() throws SQLException, IOException {
        Statement statement = databaseConfiguration.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM restaurant");
        while (resultSet.next()) {
            Restaurant r = new Restaurant();
            r.setName(resultSet.getString("name"));
            r.setaddress(resultSet.getString("address"));
            String s = resultSet.getString("Menu");
            String[] fv = s.split(";");
            int j = 0;
            while (j < fv.length){
                r.addFood(Food.ToFood(fv[j]));
                j+=1;
        }

        }
        statement.close();
    }
    public void create(Restaurant restaurant) {
        try{
        String query = "INSERT INTO restaurant (id,name, address, Menu) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,restaurant.getId());
        preparedStatement.setString(2,restaurant.getName());
        preparedStatement.setString(3, restaurant.getaddress());
        String s="";
        int ok=1;
        for(Food f:restaurant.getMenu()){
            if(ok==1)
                ok=0;
            else
                s+=';';
            s+=Food.ToFile(f);

        }
        preparedStatement.setString(4,s);
        preparedStatement.execute();
        preparedStatement.close();}
        catch(Exception e){

        }

    }

    public void update(Restaurant restaurant)throws SQLException{
        String query = "UPDATE restaurant SET name = ?, address = ?, Menu = ? where id = ?";
        PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setString(1, restaurant.getName());
        preparedStatement.setString(2,restaurant.getaddress());
        String s="";
        int ok=1;
        for(Food f:restaurant.getMenu()){
            if(ok==1)
                ok=0;
            else
                s+=';';
            s+=Food.ToFile(f);

        }
        preparedStatement.setString(3,s);
        preparedStatement.setInt(4,restaurant.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public void delete(Restaurant restaurant) throws SQLException{
        String query = "DELETE FROM restaurant WHERE id = ?";
        PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,restaurant.getId());
        preparedStatement.execute();
        preparedStatement.close();
    }
}
