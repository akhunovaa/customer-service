package ru.sbt.azatakhunov.customerservice.domain.exception;

public class DuplicatedIDException extends RuntimeException {

    public DuplicatedIDException() {
        super("ID is duplicated!");
    }
}