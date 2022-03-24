package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.sbt.azatakhunov.customerservice.port.adapter.service.client.OrderRestClientService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.OrderInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class HttpOrderAdapter implements OrderAdapter {

    private final OrderRestClientService orderRestClientService;

    public HttpOrderAdapter(@RestClient OrderRestClientService orderRestClientService) {
        this.orderRestClientService = orderRestClientService;
    }

    @Override
    public List<OrderInfoDTO> getTradeListByCustomerID(String id) {
        List<OrderInfoDTO> orderInfoDTOList = orderRestClientService.getTradeListByCustomerID(id);
        return orderInfoDTOList;
    }
}
