package dev.nhaiden.lab14_bsp2.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
