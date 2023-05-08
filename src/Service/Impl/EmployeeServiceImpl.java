package Service.Impl;

import Service.EmployeesService;
import Service.MainService;
import Service.OrderInterface;
import Service.RestaurantService;
import Util.Rank;
import exception.InvalidBirthDate;
import exception.NotEnoughExperience;
import model.Cook;
import model.Driver;
import model.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import static validation.EmployeeValidation.Age;
import static validation.EmployeeValidation.Experience;

public class EmployeeServiceImpl implements EmployeesService {
    private static EmployeeServiceImpl instance=null;

    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner dsc;

    private static Scanner csc;
    private EmployeeServiceImpl() throws FileNotFoundException {
        dsc = new Scanner(new File("src\\Util\\Drivers.csv"));
        dsc.useDelimiter("\n");
        csc = new Scanner(new File("src\\Util\\Cooks.csv"));
        csc.useDelimiter("\n");
    }

    public static EmployeeServiceImpl  getInstance() throws FileNotFoundException {
        if (instance==null)
            instance=new EmployeeServiceImpl();
        return instance;
    }

    private static SortedSet<Employee> employees=new TreeSet(new Comparator<Employee>() {
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
        if(valid==true && employees.contains(employee)==false){
            employees.add(employee);
            if(employee instanceof Driver){
                drivers.add((Driver)employee);
            service.Audit("add employee");
            }
        }
    }

    @Override
    public SortedSet<Employee> getEmployees() throws IOException {
        service.Audit("Get employees");
        return employees;
    }

    @Override
    public PriorityQueue<Driver> getDrivers() {
        return drivers;
    }

    @Override
    public void read(Employee e) throws Exception {     //citim datele intr-un obiect
        OrderInterface orders=OrderServiceImpl.getInstance();
        RestaurantService restaurants=RestaurantServiceImpl.getInstance();
        if(!dsc.hasNext())
            return;
        if(!csc.hasNext())
            return;                 //verificam daca este driver sau cook pentru a lua datele din fisierul corect

        if (e instanceof Driver) {
            String lined=dsc.next();
            String [] dline=lined.split(",");
            e.setName(dline[0]);
            e.setSalary(Integer.valueOf(dline[1]));
            String date = dline[2];
            String[] d = date.split("/");
            int luna = Integer.valueOf(d[0]);
            int zi = Integer.valueOf(d[1]);
            int an = Integer.valueOf(d[2]);
            e.setBirthdate(new Date(an - 1900, luna-1, zi));
            e.setExperience(Integer.valueOf((dline[3])));
            ((Driver) e).setCar(dline[4]);
            int i=5;
            while(i<dline.length){
                ((Driver) e).addOrder(orders.getallOrders().get(Integer.valueOf(dline[i])));
                i+=1;           //in fisier se afla indicele fiecarei comenzi
            }
        }
        if (e instanceof Cook){
            String linec= csc.next();
            String [] cline=linec.split(",");
            e.setName(cline[0]);
            e.setSalary(Integer.valueOf(cline[1]));
            String date = cline[2];
            String[] d = date.split("/");
            int luna = Integer.valueOf(d[0]);
            int zi = Integer.valueOf(d[1]);
            int an = Integer.valueOf(d[2]);
            e.setBirthdate(new Date(an - 1900, luna-1, zi));
            e.setExperience(Integer.valueOf((cline[3])));
            ((Cook) e).setRank(Rank.valueOf(cline[4]));
            ((Cook) e).setRestaurant(restaurants.getRestaurants().get(Integer.valueOf(cline[5])));
            //in fisier se afla indicele restaurantului
        }
        addEmployee(e);

    }

    public void write() throws Exception {      //scrie feicare angajat in fisierul corespunzator
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        FileWriter fw = new FileWriter("src\\Util\\Drivers.csv",false);
        FileWriter gw=new FileWriter("src\\Util\\Cooks.csv",false);
        for(Employee e: employees){
            String s=e.getName()+','+Integer.toString(e.getSalary())+','+df.format(e.getBirthdate())+','+Integer.toString(e.getExperience())+',';
            if (e instanceof Driver) {
                s += ((Driver) e).getCar();
                if(((Driver) e).getOrders()!=null)
                    {while(!((Driver) e).getOrders().isEmpty()){
                        s+=','+Integer.toString(((Driver) e).getOrders().poll().getId());
                    }   //pentru fiecare comanda retinem ca atribut indicele din vectorul de comenzi
                    }
                fw.write(s);
                fw.write("\n");
            }
            if(e instanceof Cook) {
                s+=((Cook) e).getRank().toString();
                s+=','+Integer.toString(((Cook) e).getRestaurant().getId()); //pentru fiecare restaurant retinem indicele din vectorul de restaurante
                gw.write(s);
                gw.write("\n");
            }
        }
        fw.close();
        gw.close();
    }

        public void load() throws Exception {       //resetam scanner-ul pentru a citi tot
            csc.reset();
            dsc.reset();
            csc.useDelimiter("\n");
            dsc.useDelimiter("\n");
            while(csc.hasNext()){
                read(new Cook());
            }
            while(dsc.hasNext()){
                read(new Driver());
            }

        }
    }

