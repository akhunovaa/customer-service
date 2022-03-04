package ru.sbt.azatakhunov.customerservice.domain.exception;

public class PasswordNotMatchedException extends RuntimeException {

    public PasswordNotMatchedException() {
        super("Password is not matched!");
    }

}