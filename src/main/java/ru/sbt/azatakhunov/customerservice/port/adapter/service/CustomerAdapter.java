package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import java.util.List;

public interface CustomerAdapter {

    List<CustomerInfoDTO> getAllCustomerList();

}
