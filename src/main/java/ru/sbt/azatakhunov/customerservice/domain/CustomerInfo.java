package ru.sbt.azatakhunov.customerservice.domain;

import lombok.Builder;
import lombok.Data;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordNotMatchedException;
import ru.sbt.azatakhunov.customerservice.domain.exception.PasswordTooShortException;

@Builder(toBuilder = true)
@Data
public class CustomerInfo {

    private String id;
    private String pw;
    private String name;
    private String address;
    private String registeredDay;

    public void removeCustomerInfo(String id, String pw) {
        if (!this.pw.equals(pw)) {
            throw new PasswordNotMatchedException();
        }

//        this.apply(new CustomerRemovedEvent(id));
    }

    public void changeCustomerInfo(String pw, String name, String address) {
        if (pw.length() < 8) {
            throw new PasswordTooShortException();
        }

//        this.apply(new CustomerUpdatedEvent(this.id, pw, name, address));
    }
}
