package org.lobosze.api.data;

import lombok.Data;

import java.util.List;

@Data
public class CountryInfo {
    private String country;
    private List<CityInfo> cities;
    String prefix;
    String timeZone;
    String currency;
    String language;
}
