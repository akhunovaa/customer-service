package ru.sbt.azatakhunov.customerservice.application;

import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.port.adapter.persistance.CustomerProjectionRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerQueryService {

    private final CustomerProjectionRepository customerProjectionRepository;

    public CustomerQueryService(CustomerProjectionRepository customerProjectionRepository) {
        this.customerProjectionRepository = customerProjectionRepository;
    }

    public CustomerInfo getCustomerById(String id) {
        return customerProjectionRepository.findById(id);
    }
}
