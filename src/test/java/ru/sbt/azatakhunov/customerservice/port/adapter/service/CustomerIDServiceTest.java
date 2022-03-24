package ru.sbt.azatakhunov.customerservice.port.adapter.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.domain.CustomerIDService;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class CustomerIDServiceTest {

    @InjectMock
    CustomerAdapter customerAdapter;

    @Inject
    CustomerIDService customerIDService;

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
    @DisplayName("Test Check Duplicated ID Contains")
    void customerTest_testCheckDuplicatedID_contains() {
        Mockito.when(customerAdapter.getAllCustomerList()).thenReturn(customerInfoDTOList);
        boolean duplicatedIdentifier_13 = customerIDService.checkDuplicatedID("13");
        boolean duplicatedIdentifier_14 = customerIDService.checkDuplicatedID("14");
        boolean duplicatedIdentifier_15 = customerIDService.checkDuplicatedID("15");
        Assertions.assertTrue(duplicatedIdentifier_13, "Customers Duplicated Identifiers Check Service Test Fails");
        Assertions.assertTrue(duplicatedIdentifier_14, "Customers Duplicated Identifiers Check Service Test Fails");
        Assertions.assertTrue(duplicatedIdentifier_15, "Customers Duplicated Identifiers Check Service Test Fails");
    }

    @Test
    @DisplayName("Test Check Duplicated ID Not Contains")
    void customerTest_testCheckDuplicatedID_notContains() {
        Mockito.when(customerAdapter.getAllCustomerList()).thenReturn(customerInfoDTOList);
        boolean duplicatedIdentifier_16 = customerIDService.checkDuplicatedID("16");
        boolean duplicatedIdentifier_17 = customerIDService.checkDuplicatedID("17");
        boolean duplicatedIdentifier_18 = customerIDService.checkDuplicatedID("18");
        Assertions.assertFalse(duplicatedIdentifier_16, "Customers Duplicated Identifiers Check Service Test Fails");
        Assertions.assertFalse(duplicatedIdentifier_17, "Customers Duplicated Identifiers Check Service Test Fails");
        Assertions.assertFalse(duplicatedIdentifier_18, "Customers Duplicated Identifiers Check Service Test Fails");
    }

}