package Service.Impl;

import Service.EmployeesService;
import exception.InvalidBirthDate;
import exception.NotEnoughExperience;
import model.Driver;
import model.Employee;

import java.util.*;

import static validation.EmployeeValidation.Age;
import static validation.EmployeeValidation.Experience;

public class EmployeeServiceImpl implements EmployeesService {
    private static SortedSet employees=new TreeSet(new Comparator<Employee>() {
        public int compare(Employee emp1, Employee emp2) {
            return emp1.getName().compareTo(emp2.getName());
        }   //angajati sortati in ordine alfabetica
    });
    private static PriorityQueue<Driver> drivers=new PriorityQueue<>(new Comparator<Driver>() {
        @Override
        public int compare(Driver o1, Driver o2) {
            return Integer.compare(o1.getOrders().size(),o2.getOrders().size());
        }
    });     //soferi sortati in functie de nr de comenzi
    @Override
    public void addEmployee(Employee employee) throws Exception{
        boolean valid=true;
        try{
            if(Age(employee)==false)
                throw new InvalidBirthDate("The minimum age is 18");
        }
        catch(InvalidBirthDate exception){
            valid=false;
            System.out.println(exception.getMessage());
        }

        try{
            if(Experience(employee)==false)
                throw new NotEnoughExperience("Minimum experience is 2 years");
        }
        catch(NotEnoughExperience exception){
            valid=false;
            System.out.println(exception.getMessage());
        }
        if(valid==true){
            employees.add(employee);
            if(employee instanceof Driver)
                drivers.add((Driver)employee);
        }
    }

    @Override
    public SortedSet<Employee> getEmployees(){
        return employees;
    }

    @Override
    public PriorityQueue<Driver> getDrivers() {
        return drivers;
    }
}
