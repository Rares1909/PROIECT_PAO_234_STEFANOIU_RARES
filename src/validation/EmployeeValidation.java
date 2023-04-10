package validation;

import model.Employee;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class EmployeeValidation {
    public static  boolean Experience(Employee e){
        if(e.getExperience()>2)
            return true;
        else
            return false;
    }

    public static boolean Age(Employee e){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(e.getBirthdate());
        if(LocalDate.now().getYear() - calendar.get(Calendar.YEAR) < 18)
            return false;
        return true;
    }
}
