package ru.sbt.azatakhunov.customerservice.web.controller.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link CustomerInfoDTO} class.
 */
@DisplayName("Data Transfer Object Test")
class CustomerInfoDTOTest extends DtoTest<CustomerInfoDTO> {

    @Test
    void test_ErrorData_Test() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        CustomerInfoDTO customerInfoDTO_one = CustomerInfoDTO.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .registeredDay(registeredDay)
                .build();

        CustomerInfoDTO customerInfoDTO_two = CustomerInfoDTO.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .registeredDay(registeredDay)
                .build();


        Assertions.assertEquals(customerInfoDTO_one, customerInfoDTO_two, "Assertion of two objects fails!");
        Assertions.assertEquals(customerInfoDTO_one.toString(), customerInfoDTO_two.toString(), "Assertion of two objects toString() method fails!");
    }

    @Override
    protected CustomerInfoDTO getInstance() {
        return CustomerInfoDTO.builder().build();
    }
}
