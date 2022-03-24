package ru.sbt.azatakhunov.customerservice.domain;

public interface CustomerPriceService {
    int getPriceOfSignedTrades(String customerID);
}
