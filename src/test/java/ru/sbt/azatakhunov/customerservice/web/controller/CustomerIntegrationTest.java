package ru.sbt.azatakhunov.customerservice.web.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerIntegrationTest {


    @Test
    void testCustomerId_whenMakingGetRequestToCustomerEndpoint_thenReturnCustomer() {

        RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .when()
                .get("/customers/13")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }
}
