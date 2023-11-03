package Exceptions;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists(String message){
        super(message);
        System.err.println(message);
    }
}
