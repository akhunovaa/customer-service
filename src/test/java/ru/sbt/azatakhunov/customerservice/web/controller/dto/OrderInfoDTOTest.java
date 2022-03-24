package ru.sbt.azatakhunov.customerservice.web.controller.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link OrderInfoDTO} class.
 */
@DisplayName("Data Transfer Object Test")
class OrderInfoDTOTest extends DtoTest<OrderInfoDTO> {

    @Test
    void test_ErrorData_Test() {

        OrderInfoDTO orderInfoDTO_one = OrderInfoDTO.builder()
                .id("13")
                .price(113)
                .quantity(13)
                .build();

        OrderInfoDTO orderInfoDTO_two = OrderInfoDTO.builder()
                .id("13")
                .price(113)
                .quantity(13)
                .build();;


        Assertions.assertEquals(orderInfoDTO_one, orderInfoDTO_two, "Assertion of two objects fails!");
        Assertions.assertEquals(orderInfoDTO_one.toString(), orderInfoDTO_two.toString(), "Assertion of two objects toString() method fails!");
    }

    @Override
    protected OrderInfoDTO getInstance() {
        return OrderInfoDTO.builder().build();
    }
}
