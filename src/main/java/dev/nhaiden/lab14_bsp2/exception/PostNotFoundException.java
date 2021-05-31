package dev.nhaiden.lab14_bsp2.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String msg) {
        super(msg);
    }
}
