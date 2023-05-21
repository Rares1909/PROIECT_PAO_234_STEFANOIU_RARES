package Service.Databases;

import Service.EmployeesService;
import Service.Impl.EmployeeServiceImpl;
import Service.Impl.OrderServiceImpl;
import Service.Impl.RestaurantServiceImpl;
import Service.OrderInterface;
import Service.RestaurantService;
import Util.Rank;
import config.DatabaseConfiguration;
import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EmployeeDatabase {
    private final DatabaseConfiguration databaseConfiguration;

    public EmployeeDatabase(DatabaseConfiguration databaseCon) {
        databaseConfiguration = databaseCon;
    }

    public void create(Employee employee) throws SQLException {
        try{
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (employee instanceof Driver){
            String query = "INSERT INTO driver (name, birthdate, salary, experience, car, orders) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setString(2, (df.format(employee.getBirthdate())));
            preparedStatement.setInt(3,employee.getSalary());
            preparedStatement.setInt(4,employee.getExperience());
            preparedStatement.setString(5,((Driver) employee).getCar());
            String s="";
            int ok=1;
            for(Order o: ((Driver) employee).getOrders()){
                if(ok==1)
                    ok=0;
                else
                    s+=';';
                s+=Integer.toString(o.getId());
            }
            preparedStatement.setString(6,s);
            preparedStatement.execute();
            preparedStatement.close();}
            if(employee instanceof Cook){
                String query = "INSERT INTO cook (name, birthdate, salary, experience, `rank`, id_restaurant) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
                preparedStatement.setString(1,employee.getName());
                preparedStatement.setString(2, (df.format(employee.getBirthdate())));
                preparedStatement.setInt(3,employee.getSalary());
                preparedStatement.setInt(4,employee.getExperience());
                preparedStatement.setString(5,((Cook) employee).getRank().toString());
                preparedStatement.setInt(6,((Cook) employee).getRestaurant().getId());
                preparedStatement.execute();
                preparedStatement.close();
            }}
        catch(Exception e){

        }
        }
        public void read_driver() throws Exception {
            OrderInterface orders_service = OrderServiceImpl.getInstance();
            EmployeesService employeesService= EmployeeServiceImpl.getInstance();
            String query = "select * from driver";
            Statement statement = databaseConfiguration.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Employee e = new Driver();
                e.setName(resultSet.getString("name"));
                String[] d = resultSet.getString("birthdate").split("/");
                int luna = Integer.valueOf(d[0]);
                int zi = Integer.valueOf(d[1]);
                int an = Integer.valueOf(d[2]);
                e.setBirthdate(new Date(an - 1900, luna - 1, zi));
                e.setSalary(resultSet.getInt("salary"));
                e.setExperience(resultSet.getInt("experience"));
                ((Driver) e).setCar(resultSet.getString("car"));
                Queue<Order> orders = new LinkedList<>();
                String[] s = resultSet.getString("orders").split(";");
                if(!s[0].isEmpty()){
                for (String nr : s)
                    orders.add(orders_service.getallOrders().get(Integer.valueOf(nr)));
                ((Driver) e).setOrders(orders);}

                employeesService.addEmployee(e);

            }
            statement.close();

        }
        public void read_cook() throws Exception {
            EmployeesService employeesService=EmployeeServiceImpl.getInstance();
            RestaurantService restaurantService= RestaurantServiceImpl.getInstance();
            String query = "select * from cook";
            Statement statement = databaseConfiguration.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Employee e =new Cook();
                e.setName(resultSet.getString("name"));
                e.setSalary(resultSet.getInt("salary"));
                String[] d = resultSet.getString("birthdate").split("/");
                int luna = Integer.valueOf(d[0]);
                int zi = Integer.valueOf(d[1]);
                int an = Integer.valueOf(d[2]);
                e.setBirthdate(new Date(an - 1900, luna - 1, zi));
                e.setExperience(resultSet.getInt("experience"));
                ((Cook) e).setRank(Rank.valueOf(resultSet.getString("rank")));
                ((Cook) e).setRestaurant(restaurantService.getRestaurants().get(resultSet.getInt("id_restaurant")));

                employeesService.addEmployee(e);

            }
            statement.close();
        }

    public void update(Employee employee) throws SQLException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (employee instanceof Driver){
            String query = "Update driver set birthdate=?, salary=?, experience=?, car=?, orders=? where name=?";
            PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStatement.setString(6,employee.getName());
            preparedStatement.setString(1, (df.format(employee.getBirthdate())));
            preparedStatement.setInt(2,employee.getSalary());
            preparedStatement.setInt(3,employee.getExperience());
            preparedStatement.setString(4,((Driver) employee).getCar());
            String s="";
            int ok=1;
            for(Order o: ((Driver) employee).getOrders()){
                if(ok==1)
                    ok=0;
                else
                    s+=';';
                s+=Integer.toString(o.getId());
            }
            preparedStatement.setString(5,s);
            preparedStatement.executeUpdate();
            preparedStatement.close();}

        if (employee instanceof Cook){
            String query = "Update cook set birthdate=?, salary=?, experience=?, `rank`=?, id_restaurant=? where name=?";
            PreparedStatement preparedStatement = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStatement.setString(6,employee.getName());
            preparedStatement.setString(1, (df.format(employee.getBirthdate())));
            preparedStatement.setInt(2,employee.getSalary());
            preparedStatement.setInt(3,employee.getExperience());
            preparedStatement.setString(4,((Cook) employee).getRank().toString());
            preparedStatement.setInt(5,((Cook) employee).getRestaurant().getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }
    public void delete(Employee employee) throws SQLException{
        if (employee instanceof Driver){
        String query = "DELETE FROM driver WHERE name = ?";
        PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
        preparedStatement.setString(1,employee.getName());
        preparedStatement.execute();
        preparedStatement.close();}
        if (employee instanceof Cook){
            String query = "DELETE FROM cook WHERE name = ?";
            PreparedStatement preparedStatement=databaseConfiguration.getConnection().prepareStatement(query);
            preparedStatement.setString(1,employee.getName());
            preparedStatement.execute();
            preparedStatement.close();
        }
    }
    }

