package ru.sbt.azatakhunov.customerservice.application;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.domain.CustomerFactoryService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerIDService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.domain.exception.DuplicatedIDException;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordNotMatchedException;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordTooShortException;
import ru.sbt.azatakhunov.customerservice.port.adapter.persistance.CustomerRepository;
import ru.sbt.azatakhunov.customerservice.web.controller.dto.CustomerInfoDTO;

import javax.inject.Inject;

@QuarkusTest
class CustomerApplicationServiceTest {

    @InjectMock
    CustomerIDService customerIDService;

    @InjectMock
    CustomerRepository customerRepository;

    @InjectMock
    CustomerFactoryService customerFactoryService;

    @Inject
    CustomerApplicationService customerApplicationService;

    CustomerInfoDTO customerInfoDTO;
    CustomerInfo customerInfo;

    @BeforeEach
    void beforeEach() {
        customerInfoDTO = CustomerInfoDTO.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .build();

        customerInfo = CustomerInfo.builder()
                .id("13")
                .pw("1234567890")
                .name("test_name")
                .address("test_address")
                .registeredDay(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()))
                .build();
    }

    @Test
    @DisplayName("Test assert DuplicatedIDException")
    void testCustomer_shouldRegisterExistingIDCustomer() {
        Mockito.when(customerIDService.checkDuplicatedID("13")).thenReturn(true);
        DuplicatedIDException duplicatedIDException = Assertions.assertThrows(
                DuplicatedIDException.class,
                () -> customerApplicationService.registerCustomer("13", customerInfoDTO.getPw(),
                        customerInfoDTO.getName(), customerInfoDTO.getAddress()),
                "Expected registerCustomer() to throw, but it didn't"
        );
        Assertions.assertEquals("ID is duplicated!", duplicatedIDException.getMessage());
    }

    @Test
    @DisplayName("Test assert Registration Date")
    void testCustomer_shouldRegisterCustomerWithDate() {
        Mockito.when(customerIDService.checkDuplicatedID("13")).thenReturn(false);
        Mockito.when(customerFactoryService.createCustomerInfo(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(customerInfo);
        Mockito.when(customerRepository.save(customerInfo)).thenReturn(customerInfo);

        CustomerInfo testedCustomer = Assertions.assertDoesNotThrow(() ->
                customerApplicationService.registerCustomer("13", customerInfoDTO.getPw(),
                        customerInfoDTO.getName(), customerInfoDTO.getAddress()));
        Assertions.assertNotNull(testedCustomer);
        Assertions.assertEquals(customerInfo.getId(), testedCustomer.getId(), "Customer ID assertion fails");
        Assertions.assertEquals(customerInfo.getPw(), testedCustomer.getPw(), "Customer PW assertion fails");
        Assertions.assertEquals(customerInfo.getName(), testedCustomer.getName(), "Customer Name assertion fails");
        Assertions.assertEquals(customerInfo.getAddress(), testedCustomer.getAddress(), "Customer Address assertion fails");
        Assertions.assertEquals(customerInfo.getRegisteredDay(), testedCustomer.getRegisteredDay(), "Customer Registered day assertion fails");
    }

    @Test
    @DisplayName("Test Get Customer By ID")
    void testCustomer_getCustomerByID() {
        Mockito.when(customerRepository.findById("13")).thenReturn(customerInfo);

        CustomerInfo testedCustomer = customerApplicationService.getCustomerByID("13");
        Assertions.assertNotNull(testedCustomer);
        Assertions.assertEquals(customerInfo.getId(), testedCustomer.getId(), "Customer ID assertion fails");
        Assertions.assertEquals(customerInfo.getPw(), testedCustomer.getPw(), "Customer PW assertion fails");
        Assertions.assertEquals(customerInfo.getName(), testedCustomer.getName(), "Customer Name assertion fails");
        Assertions.assertEquals(customerInfo.getAddress(), testedCustomer.getAddress(), "Customer Address assertion fails");
        Assertions.assertEquals(customerInfo.getRegisteredDay(), testedCustomer.getRegisteredDay(), "Customer Registered day assertion fails");
    }

    @Test
    @DisplayName("Test Withdraw Customer")
    void testCustomer_withdrawCustomerPw() {
        Mockito.when(customerRepository.findById("13")).thenReturn(customerInfo);
        Mockito.when(customerRepository.save(customerInfo)).thenReturn(customerInfo);

        CustomerInfo testedCustomer = Assertions.assertDoesNotThrow(() ->
                customerApplicationService.withdrawCustomer("13", customerInfo.getPw()));

        Assertions.assertEquals(customerInfo.getPw(), testedCustomer.getPw(), "Customer Withdraw assertion fails");
    }

    @Test
    @DisplayName("Test Withdraw Customer Exception")
    void testCustomer_withdrawCustomerPwException() {
        Mockito.when(customerRepository.findById("13")).thenReturn(customerInfo);
        Mockito.when(customerRepository.save(customerInfo)).thenReturn(customerInfo);

        Mockito.when(customerIDService.checkDuplicatedID("13")).thenReturn(true);
        PasswordNotMatchedException passwordNotMatchedException = Assertions.assertThrows(
                PasswordNotMatchedException.class,
                () -> customerApplicationService.withdrawCustomer("13", "not_equals_password"),
                "Expected withdrawCustomer() to throw PasswordNotMatchedException, but it didn't"
        );
        Assertions.assertEquals("Password is not matched!", passwordNotMatchedException.getMessage());
    }

    @Test
    @DisplayName("Test Update Customer")
    void testCustomer_updateCustomer() {
        Mockito.when(customerRepository.findById("13")).thenReturn(customerInfo);
        Mockito.when(customerRepository.save(customerInfo)).thenReturn(customerInfo);
        CustomerInfo testedCustomer = Assertions.assertDoesNotThrow(() ->
                customerApplicationService.updateCustomer("13", customerInfo.getPw(), customerInfo.getName(), customerInfo.getAddress()));

        Assertions.assertEquals(customerInfo.getId(), testedCustomer.getId(), "Customer ID assertion fails");
        Assertions.assertEquals(customerInfo.getPw(), testedCustomer.getPw(), "Customer PW assertion fails");
        Assertions.assertEquals(customerInfo.getName(), testedCustomer.getName(), "Customer Name assertion fails");
        Assertions.assertEquals(customerInfo.getAddress(), testedCustomer.getAddress(), "Customer Address assertion fails");
        Assertions.assertEquals(customerInfo.getRegisteredDay(), testedCustomer.getRegisteredDay(), "Customer Registered day assertion fails");
    }

    @Test
    @DisplayName("Test Update Customer Exception")
    void testCustomer_updateCustomerException() {
        Mockito.when(customerRepository.findById("13")).thenReturn(customerInfo);
        Mockito.when(customerRepository.save(customerInfo)).thenReturn(customerInfo);

        Mockito.when(customerIDService.checkDuplicatedID("13")).thenReturn(true);
        PasswordTooShortException passwordTooShortException = Assertions.assertThrows(
                PasswordTooShortException.class,
                () -> customerApplicationService.updateCustomer("13", "1234567", customerInfo.getName(), customerInfo.getAddress()),
                "Expected updateCustomer() to throw PasswordTooShortException, but it didn't"
        );
        Assertions.assertEquals("password is too short!", passwordTooShortException.getMessage());
    }

}