package ru.sbt.azatakhunov.customerservice.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderInfoDTO {
    private String id;
    private int price;
    private int quantity;
}
