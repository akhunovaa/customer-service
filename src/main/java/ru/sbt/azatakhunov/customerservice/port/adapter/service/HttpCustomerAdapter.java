package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.sbt.azatakhunov.customerservice.port.adapter.service.client.DeliveryRestClientService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class HttpCustomerAdapter implements CustomerAdapter {

    private final DeliveryRestClientService deliveryRestClientService;

    public HttpCustomerAdapter(@RestClient DeliveryRestClientService deliveryRestClientService) {
        this.deliveryRestClientService = deliveryRestClientService;
    }

    @Override
    public List<CustomerInfoDTO> getAllCustomerList() {
        List<CustomerInfoDTO> customerInfoDTOList = deliveryRestClientService.getAllCustomerList();
        return customerInfoDTOList;
    }
}
