package ru.sbt.azatakhunov.customerservice.web.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.application.CustomerApplicationService;
import ru.sbt.azatakhunov.customerservice.application.CustomerQueryService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerIDService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerIntegrationTest {

    @InjectMock
    CustomerApplicationService customerApplicationService;

    @InjectMock
    CustomerIDService customerIDService;

    @InjectMock
    CustomerQueryService customerQueryService;

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
    @DisplayName("Test Customers Endpoint HTTP GET Method")
    void testCustomer_whenMakingGETRequestToCustomerEndpoint_thenReturnCustomer() {
        Mockito.when(customerQueryService.getCustomerById(customerInfoDTO.getId()))
                .thenReturn(customerInfo);

        CustomerInfo result = RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .get("/v1/customers/13")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .as(CustomerInfo.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, customerInfo);
    }

    @Test
    @DisplayName("Test Customers Endpoint HTTP POST Method")
    void testCustomer_whenMakingPOSTRequestToCustomerEndpoint_thenReturnCustomer() {
        Mockito.when(customerApplicationService.registerCustomer(customerInfoDTO.getId(), customerInfoDTO.getPw(),
                        customerInfoDTO.getName(), customerInfoDTO.getAddress()))
                .thenReturn(customerInfo);
        Mockito.when(customerIDService.checkDuplicatedID(customerInfoDTO.getId()))
                .thenReturn(false);

        CustomerInfo result = RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(customerInfoDTO)
                .when()
                .post("/v1/customers")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .as(CustomerInfo.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, customerInfo);
    }

    @Test
    @DisplayName("Test Customers Endpoint HTTP DELETE Method")
    void testCustomer_whenMakingDELETERequestToCustomerEndpoint_thenReturnCustomer() {
        Mockito.when(customerApplicationService.withdrawCustomer(customerInfoDTO.getId(), customerInfoDTO.getPw()))
                .thenReturn(customerInfo);

        CustomerInfo result = RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(customerInfoDTO)
                .when()
                .delete("/v1/customers/13")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .as(CustomerInfo.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, customerInfo);
    }

    @Test
    @DisplayName("Test Customers Endpoint HTTP PUT Method")
    void testCustomer_whenMakingPUTRequestToCustomerEndpoint_thenReturnCustomer() {
        Mockito.when(customerApplicationService.updateCustomer(customerInfoDTO.getId(), customerInfoDTO.getPw(),
                        customerInfoDTO.getName(), customerInfoDTO.getAddress()))
                .thenReturn(customerInfo);

        CustomerInfo result = RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(customerInfoDTO)
                .when()
                .put("/v1/customers/13")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .extract()
                .as(CustomerInfo.class);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, customerInfo);
    }
}
