package dev.nhaiden.lab14_bsp2.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
