package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class HttpCustomerAdapter implements CustomerAdapter {

    @Override
    public List<CustomerInfoDTO> getAllCustomerList() {
        return null;
    }
}
