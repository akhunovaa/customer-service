package ru.sbt.azatakhunov.customerservice.domain.exception;

public class PasswordTooShortException extends RuntimeException {

    public PasswordTooShortException() {
        super("password is too short!");
    }

}