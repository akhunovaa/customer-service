package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.domain.CustomerPriceService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.OrderInfoDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class CustomerPriceServiceTest {

    @InjectMock
    OrderAdapter orderAdapter;

    @Inject
    CustomerPriceService customerPriceService;

    List<OrderInfoDTO> orderInfoDTOList;

    @BeforeEach
    void beforeEach() {
        orderInfoDTOList = new ArrayList<>(2);

        orderInfoDTOList.add(OrderInfoDTO.builder()
                .id("13")
                .price(113)
                .quantity(13)
                .build());
        orderInfoDTOList.add(OrderInfoDTO.builder()
                .id("14")
                .price(114)
                .quantity(14)
                .build());
    }

    @Test
    @DisplayName("Test Get Price Of Signed Trades")
    void customerTest_getPriceOfSignedTrades() {
        Mockito.when(orderAdapter.getTradeListByCustomerID("13")).thenReturn(orderInfoDTOList);

        int actualPriceOfCustomer = customerPriceService.getPriceOfSignedTrades("13");
        Assertions.assertEquals(3065, actualPriceOfCustomer, "Test Get Price Of Signed Trades fails");
    }

}