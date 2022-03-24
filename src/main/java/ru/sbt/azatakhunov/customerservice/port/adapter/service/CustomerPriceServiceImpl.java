package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import ru.sbt.azatakhunov.customerservice.domain.CustomerPriceService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.OrderInfoDTO;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CustomerPriceServiceImpl implements CustomerPriceService {

    private final OrderAdapter orderAdapter;

    public CustomerPriceServiceImpl(OrderAdapter orderAdapter) {
        this.orderAdapter = orderAdapter;
    }

    @Override
    public int getPriceOfSignedTrades(String customerID) {

        int totalPrice = 0;
        List<OrderInfoDTO> orderInfoDTOList = orderAdapter.getTradeListByCustomerID(customerID);

        for (OrderInfoDTO trade : orderInfoDTOList) {
            totalPrice += trade.getPrice() * trade.getQuantity();
        }
        return totalPrice;
    }
}
