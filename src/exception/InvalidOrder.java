package exception;

public class InvalidOrder extends RuntimeException{
    public InvalidOrder(String m){
        super(m);
    }
}
