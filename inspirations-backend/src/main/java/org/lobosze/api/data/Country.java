package org.lobosze.api.data;

import lombok.Data;

import java.util.List;

@Data
public class Country {
    String name;
    String description;
    List<City> cities;
}
