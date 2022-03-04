package ru.sbt.azatakhunov.customerservice.domain;

public interface CustomerIDService {
    boolean checkDuplicatedID(String id);
}
