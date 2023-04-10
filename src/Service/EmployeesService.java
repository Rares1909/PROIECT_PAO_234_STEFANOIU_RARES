package Service;

import model.Driver;
import model.Employee;

import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

public interface EmployeesService {
    void addEmployee(Employee employee) throws Exception;
    SortedSet<Employee> getEmployees() ;

    PriorityQueue<Driver> getDrivers();
}
