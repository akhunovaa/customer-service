package ru.sbt.azatakhunov.customerservice.application;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.port.adapter.persistance.CustomerProjectionRepository;

import javax.inject.Inject;

@QuarkusTest
class CustomerQueryServiceTest {

    @InjectMock
    CustomerProjectionRepository customerProjectionRepository;

    @Inject
    CustomerQueryService customerQueryService;

    CustomerInfo customerInfo;

    @BeforeEach
    void beforeEach() {
        customerInfo = CustomerInfo.builder()
                .id("66")
                .pw("projection_repository_test")
                .name("projection_repository_test_name")
                .address("projection_repository_test_address")
                .registeredDay("projection_repository_test_registered_day")
                .build();
    }


    @Test
    void testCustomerRepository_shouldReturnCustomerById() {
        Mockito.when(customerProjectionRepository.findById("66")).thenReturn(customerInfo);
        CustomerInfo customerInfo = customerQueryService.getCustomerById("66");
        Assertions.assertNotNull(customerInfo);
        Assertions.assertEquals("66", customerInfo.getId(), "Customer Query Service ID assertion fails");
        Assertions.assertEquals("projection_repository_test", customerInfo.getPw(), "Customer Query Service PW assertion fails");
        Assertions.assertEquals("projection_repository_test_name", customerInfo.getName(), "Customer Query Service Name assertion fails");
        Assertions.assertEquals("projection_repository_test_address", customerInfo.getAddress(), "Customer Query Service Address assertion fails");
        Assertions.assertEquals("projection_repository_test_registered_day", customerInfo.getRegisteredDay(), "Customer Query Service Registered Day assertion fails");
    }
}