package ru.sbt.azatakhunov.customerservice.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sbt.azatakhunov.customerservice.domain.exception.NonIDValueException;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordTooShortException;

import javax.inject.Inject;

@QuarkusTest
class CustomerFactoryServiceTest {

    @Inject
    CustomerFactoryService customerFactoryService;

    CustomerInfo customerInfo;

    @BeforeEach
    void beforeEach() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        customerInfo = CustomerInfo.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .registeredDay(registeredDay)
                .build();
    }

    @Test
    @DisplayName("Customer object builder TEST")
    void test_createCustomerInfoBuilder() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        CustomerInfo result = customerFactoryService.createCustomerInfo("13", "1234567890", "test_name",
                "test_address", registeredDay);
        Assertions.assertEquals(customerInfo, result, "Customer object builder assertion test failed");
    }

    @Test
    @DisplayName("Customer object builder NonIDValueException TEST")
    void testExceptionNonIDValueException_createCustomerInfoBuilder() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        NonIDValueException nonIDValueException = Assertions.assertThrows(
                NonIDValueException.class,
                () -> customerFactoryService.createCustomerInfo("", "1234567890", "test_name",
                        "test_address", registeredDay),
                "Expected createCustomerInfo() to throw NonIDValueException, but it didn't"
        );
        Assertions.assertEquals("ID has no value!", nonIDValueException.getMessage());
    }

    @Test
    @DisplayName("Customer object builder PasswordTooShortException TEST")
    void testPasswordTooShortException_createCustomerInfoBuilder() {
        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        PasswordTooShortException passwordTooShortException = Assertions.assertThrows(
                PasswordTooShortException.class,
                () -> customerFactoryService.createCustomerInfo("13", "1234567", "test_name",
                        "test_address", registeredDay),
                "Expected createCustomerInfo() to throw NonIDValueException, but it didn't"
        );
        Assertions.assertEquals("password is too short!", passwordTooShortException.getMessage());
    }
}