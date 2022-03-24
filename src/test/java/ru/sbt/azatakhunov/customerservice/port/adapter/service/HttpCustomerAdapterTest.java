package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.port.adapter.service.client.DeliveryRestClientService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@DisplayName("HttpCustomerAdapter Test")
class HttpCustomerAdapterTest {

    @Inject
    CustomerAdapter customerAdapter;

    @InjectMock
    @RestClient
    DeliveryRestClientService deliveryRestClientService;

    List<CustomerInfoDTO> customerInfoDTOList;

    @BeforeEach
    void beforeEach() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        customerInfoDTOList = new ArrayList<>(3);
        customerInfoDTOList.add(CustomerInfoDTO.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name_a")
                .address("test_address_a")
                .registeredDay(registeredDay)
                .build());
        customerInfoDTOList.add(CustomerInfoDTO.builder()
                .id("14")
                .pw("0987654321")
                .name("test_name_b")
                .address("test_address_b")
                .registeredDay(registeredDay)
                .build());
        customerInfoDTOList.add(CustomerInfoDTO.builder()
                .id("15")
                .pw("5432167890")
                .name("test_name_c")
                .address("test_address_c")
                .registeredDay(registeredDay)
                .build());
    }

    @Test
    @DisplayName("HttpCustomerAdapter getAllCustomerList() Method Test")
    void test_getAllCustomerList() {
        Mockito.when(deliveryRestClientService.getAllCustomerList()).thenReturn(customerInfoDTOList);

        List<CustomerInfoDTO> customerResultList = customerAdapter.getAllCustomerList();
        Assertions.assertNotNull(customerResultList);
        Assertions.assertEquals(customerInfoDTOList.size(), customerResultList.size(), "getAllCustomerList() Method Test " +
                "customerResultList size assertion fails");
        Assertions.assertIterableEquals(customerInfoDTOList, customerResultList);
    }
}