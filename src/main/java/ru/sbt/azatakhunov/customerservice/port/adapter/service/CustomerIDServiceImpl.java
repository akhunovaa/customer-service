package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import ru.sbt.azatakhunov.customerservice.domain.CustomerIDService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CustomerIDServiceImpl implements CustomerIDService {

    private final CustomerAdapter customerAdapter;

    public CustomerIDServiceImpl(CustomerAdapter customerAdapter) {
        this.customerAdapter = customerAdapter;
    }

    @Override
    public boolean checkDuplicatedID(String id) {
        List<CustomerInfoDTO> customerInfoDTOList = customerAdapter.getAllCustomerList();

        for (CustomerInfoDTO customer : customerInfoDTOList) {
            if (id.equals(customer.getId())) {
                return true;
            }
        }
        return false;
    }
}
