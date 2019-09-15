package org.lobosze.api.data;

import lombok.Data;

import java.util.List;

@Data
public class CityInfo {
    String city;
    String iata;
    List<String> attractions;
    List<String> rating;
}
