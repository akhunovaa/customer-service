package ru.sbt.azatakhunov.customerservice.web.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.application.CustomerApplicationService;
import ru.sbt.azatakhunov.customerservice.application.CustomerQueryService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@QuarkusTest
@TestHTTPEndpoint(CustomerController.class)
class CustomerControllerTest {

    @InjectMock
    CustomerQueryService customerQueryService;

    @InjectMock
    CustomerApplicationService customerApplicationService;

    @Inject
    CustomerController customerController;

    CustomerInfo customerInfo;

    CustomerInfoDTO customerInfoDTO;

    @BeforeEach
    void beforeEach() {
        customerInfo = CustomerInfo.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .registeredDay("test_registered_day")
                .build();
        customerInfoDTO = CustomerInfoDTO.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .build();
    }


    @Test
    void testCustomer_shouldReturnCustomer() {
        Mockito.when(customerQueryService.getCustomerById("13")).thenReturn(customerInfo);
        Response response = customerController.getCustomer("13");
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response);
        CustomerInfo customerInfo = (CustomerInfo) response.getEntity();
        Assertions.assertNotNull(customerInfo);
        Assertions.assertEquals("13", customerInfo.getId(), "Response ID assertion fails");
        Assertions.assertEquals("1234567890", customerInfo.getPw(), "Response PW assertion fails");
        Assertions.assertEquals("test_name", customerInfo.getName(), "Response Name assertion fails");
        Assertions.assertEquals("test_address", customerInfo.getAddress(), "Response Address assertion fails");
        Assertions.assertEquals("test_registered_day", customerInfo.getRegisteredDay(), "Response Registered Day assertion fails");
    }

    @Test
    void testCustomer_shouldRegisterCustomer() {
        Mockito.when(customerApplicationService.registerCustomer("13", "1234567890", "test_name", "test_address"))
                .thenReturn(customerInfo);

        Response response = customerController.registerCustomer(customerInfoDTO);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response);

        CustomerInfo customerInfo = (CustomerInfo) response.getEntity();
        Assertions.assertNotNull(customerInfo);
        Assertions.assertEquals("13", customerInfo.getId(), "Response ID assertion fails");
        Assertions.assertEquals("1234567890", customerInfo.getPw(), "Response PW assertion fails");
        Assertions.assertEquals("test_name", customerInfo.getName(), "Response Name assertion fails");
        Assertions.assertEquals("test_address", customerInfo.getAddress(), "Response Address assertion fails");
        Assertions.assertNotNull(customerInfo.getRegisteredDay(), "Response Registered Day assertion fails");
    }

    @Test
    void testCustomer_shouldWithdrawCustomer() {
        Mockito.when(customerApplicationService.withdrawCustomer(customerInfoDTO.getId(), customerInfoDTO.getPw()))
                .thenReturn(customerInfo);

        Response response = customerController.withdrawCustomer("13", customerInfoDTO);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response);

        CustomerInfo customerInfo = (CustomerInfo) response.getEntity();
        Assertions.assertNotNull(customerInfo);
        Assertions.assertEquals("13", customerInfo.getId(), "Response ID assertion fails");
        Assertions.assertEquals("1234567890", customerInfo.getPw(), "Response PW assertion fails");
        Assertions.assertEquals("test_name", customerInfo.getName(), "Response Name assertion fails");
        Assertions.assertEquals("test_address", customerInfo.getAddress(), "Response Address assertion fails");
        Assertions.assertNotNull(customerInfo.getRegisteredDay(), "Response Registered Day assertion fails");
    }

    @Test
    void testCustomer_shouldUpdateCustomer() {
        Mockito.when(customerApplicationService.updateCustomer(customerInfoDTO.getId(), customerInfoDTO.getPw(),
                        customerInfoDTO.getName(), customerInfoDTO.getAddress()))
                .thenReturn(customerInfo);

        Response response = customerController.updateCustomer("13", customerInfoDTO);

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertNotNull(response);

        CustomerInfo customerInfo = (CustomerInfo) response.getEntity();
        Assertions.assertNotNull(customerInfo);
        Assertions.assertEquals("13", customerInfo.getId(), "Response ID assertion fails");
        Assertions.assertEquals("1234567890", customerInfo.getPw(), "Response PW assertion fails");
        Assertions.assertEquals("test_name", customerInfo.getName(), "Response Name assertion fails");
        Assertions.assertEquals("test_address", customerInfo.getAddress(), "Response Address assertion fails");
        Assertions.assertNotNull(customerInfo.getRegisteredDay(), "Response Registered Day assertion fails");
    }

}
