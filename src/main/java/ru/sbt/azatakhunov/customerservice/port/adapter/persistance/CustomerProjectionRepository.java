package ru.sbt.azatakhunov.customerservice.port.adapter.persistance;

import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerProjectionRepository {

    public CustomerInfo findById(String id) {
        return CustomerInfo.builder()
                .id(id)
                .build();
    }

}
