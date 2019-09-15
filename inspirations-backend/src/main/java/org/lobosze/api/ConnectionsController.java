package org.lobosze.api;

import org.lobosze.api.data.ConnectionsInput;
import org.lobosze.api.lot.LotApiClient;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
public class ConnectionsController {
    @PostMapping("/connections")
    public Map getConnections(@RequestBody ConnectionsInput input) throws IOException {
        LotApiClient client = new LotApiClient(input.getMarket(), input.getLanguage());

        String token = client.getToken();

        return client.getConnections(
                token,
                input.getFromCity(),
                input.getTargetCities(),
                input.getDepartureDate(),
                input.getReturnDate(),
                input.getPassengers()
        );
    }
}
