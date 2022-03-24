package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import ru.sbt.azatakhunov.customerservice.web.controller.dto.OrderInfoDTO;

import java.util.List;

public interface OrderAdapter {

    List<OrderInfoDTO> getTradeListByCustomerID(String id);

}
