package org.lobosze.api.data;

import lombok.Data;

@Data
public class ConnectionsInput {
    private String[] targetCities;
    private String fromCity;
    private String departureDate;
    private String returnDate;
    private String market;
    private String language;
    private int passengers;
}
