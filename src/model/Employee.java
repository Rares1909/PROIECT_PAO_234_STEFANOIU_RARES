package model;

import java.util.Date;

public  abstract class  Employee {
    protected String name;
    private int salary;

    private  Date birthdate;
    private int experience;
    public Employee(){}

    public Employee(String name, int salary, Date birthdate, int experience) {
        this.name = name;
        this.salary = salary;
        this.birthdate = birthdate;
        this.experience = experience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }


    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public int getExperience() {
        return experience;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", birthdate=" + birthdate +
                ", experience=" + experience +
                '}';
    }
}
