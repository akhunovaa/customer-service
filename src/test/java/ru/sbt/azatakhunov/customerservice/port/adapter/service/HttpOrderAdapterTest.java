package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.port.adapter.service.client.OrderRestClientService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.OrderInfoDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@DisplayName("HttpOrderAdapterTest Test")
class HttpOrderAdapterTest {

    @Inject
    OrderAdapter orderAdapter;

    @InjectMock
    @RestClient
    OrderRestClientService orderRestClientService;

    List<OrderInfoDTO> orderInfoDTOList;

    @BeforeEach
    void beforeEach() {
        orderInfoDTOList = new ArrayList<>(1);

        orderInfoDTOList.add(OrderInfoDTO.builder()
                .id("13")
                .price(113)
                .quantity(13)
                .build());
    }

    @Test
    @DisplayName("HttpOrderAdapter getTradeListByCustomerID(String id) Method Test")
    void test_getTradeListByCustomerID() {
        Mockito.when(orderRestClientService.getTradeListByCustomerID("13")).thenReturn(orderInfoDTOList);

        List<OrderInfoDTO> orderInfoDTOS = orderAdapter.getTradeListByCustomerID("13");
        Assertions.assertNotNull(orderInfoDTOS);
        Assertions.assertEquals(orderInfoDTOS.size(), orderInfoDTOList.size(), "getTradeListByCustomerID Method Test " +
                "getTradeListByCustomerID size assertion fails");
        Assertions.assertIterableEquals(orderInfoDTOS, orderInfoDTOList);
    }
}