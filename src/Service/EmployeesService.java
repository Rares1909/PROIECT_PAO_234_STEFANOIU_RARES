package Service;

import model.Driver;
import model.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

public interface EmployeesService {
    void addEmployee(Employee employee) throws Exception;
    SortedSet<Employee> getEmployees() throws IOException;

    PriorityQueue<Driver> getDrivers();

    void read(Employee e) throws Exception;

    void write()throws Exception;

    void load() throws Exception;
}
