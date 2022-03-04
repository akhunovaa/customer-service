package ru.sbt.azatakhunov.customerservice.port.adapter.persistance;

import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository {

    public CustomerInfo save(CustomerInfo customerInfo) {
        return null;
    }

    public CustomerInfo findById(String id) {
        return null;
    }
}
