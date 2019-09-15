package org.lobosze.api;

import org.lobosze.api.data.CityInfo;
import org.lobosze.api.data.CountryInfo;
import org.lobosze.api.data.Tile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class InspirationsController {

    @GetMapping("/tiles")
    public List<Tile> getTiles() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getClass().getResourceAsStream("data/tiles.json"), new TypeReference<List<Tile>>(){});
    }

    @GetMapping("/city")
    public CityInfo getCityInfo(@RequestParam(name = "iata") String iata) throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("data/fullAirports_2.json");
        ObjectMapper mapper = new ObjectMapper();

        CountryInfo[] data = mapper.readValue(stream, CountryInfo[].class);

        List<CityInfo> ret = new ArrayList<>();
        for(CountryInfo i:data) {
            for(CityInfo ci:i.getCities()) {
                if (ci.getIata().equalsIgnoreCase(iata)) {
                    return ci;
                }
            }
        }
        return null;
    }

}
