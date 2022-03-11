package ru.sbt.azatakhunov.customerservice.domain;

import ru.sbt.azatakhunov.customerservice.domain.exception.NonIDValueException;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordTooShortException;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerFactoryService {

    public CustomerInfo createCustomerInfo(String id, String pw, String name, String address, String registeredDay) {
        if (id.length() == 0) {
            throw new NonIDValueException();
        }

        if (pw.length() < 8) {
            throw new PasswordTooShortException();
        }

        return new CustomerInfo(id, pw, name, address, registeredDay);
    }

}
