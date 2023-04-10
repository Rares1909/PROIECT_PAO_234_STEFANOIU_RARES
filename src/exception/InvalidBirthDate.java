package exception;

public class InvalidBirthDate extends RuntimeException{
    public InvalidBirthDate(String message){
        super(message);
    }
}
