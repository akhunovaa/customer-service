package ru.sbt.azatakhunov.customerservice.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class CustomerInfoDTO {
    private String id;
    private String pw;
    private String name;
    private String address;
    private String registeredDay;
}
