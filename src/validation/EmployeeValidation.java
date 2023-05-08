package validation;

import Service.Impl.MainServiceImpl;
import Service.MainService;
import model.Employee;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class EmployeeValidation {
    private static MainService service;

    static {
        try {
            service = MainServiceImpl.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static  boolean Experience(Employee e) throws IOException {
        service.Audit("validate experience");
        if(e.getExperience()>2)
            return true;            //experienta minima 2 ani
        else
            return false;
    }

    public static boolean Age(Employee e) throws IOException {
        service.Audit("validate age");//varsta minima 18 ani
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(e.getBirthdate());
        if(LocalDate.now().getYear() - calendar.get(Calendar.YEAR) < 18)
            return false;
        return true;
    }
}
