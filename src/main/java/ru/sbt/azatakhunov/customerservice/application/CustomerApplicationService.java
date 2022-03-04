package ru.sbt.azatakhunov.customerservice.application;

import ru.sbt.azatakhunov.customerservice.domain.CustomerFactoryService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerIDService;
import ru.sbt.azatakhunov.customerservice.domain.CustomerInfo;
import ru.sbt.azatakhunov.customerservice.domain.exception.DuplicatedIDException;
import ru.sbt.azatakhunov.customerservice.port.adapter.persistance.CustomerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
public class CustomerApplicationService {

    private final CustomerIDService customerIDService;
    private final CustomerFactoryService customerFactoryService;
    private final CustomerRepository customerRepository;

    public CustomerApplicationService(CustomerIDService customerIDService,
                                      CustomerFactoryService customerFactoryService, CustomerRepository customerRepository) {
        this.customerIDService = customerIDService;
        this.customerFactoryService = customerFactoryService;
        this.customerRepository = customerRepository;
    }

    public CustomerInfo getCustomerByID(String id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public CustomerInfo registerCustomer(String id, String pw, String name, String address) {
        if (customerIDService.checkDuplicatedID(id)) {
            throw new DuplicatedIDException();
        }

        String registeredDay = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        CustomerInfo newCustomer = customerFactoryService.createCustomerInfo(id, pw, name, address, registeredDay);
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public CustomerInfo withdrawCustomer(String id, String pw) {
        CustomerInfo customer = customerRepository.findById(id);
        customer.removeCustomerInfo(id, pw);
        customerRepository.save(customer);
        return customer;
    }


    public CustomerInfo updateCustomer(String id, String pw, String name, String address) {
        CustomerInfo customer = customerRepository.findById(id);
        customer.changeCustomerInfo(pw, name, address);
        customerRepository.save(customer);
        return customer;
    }
}
