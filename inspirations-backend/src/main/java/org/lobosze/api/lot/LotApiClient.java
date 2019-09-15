package org.lobosze.api.lot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import okhttp3.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LotApiClient {
    public static final String GET_TOKEN_URL = "https://api.lot.com/flights-dev/v2/auth/token/get";
    private static final String CONNECTIONS_URL = "https://api.lot.com/flights-dev/v2/booking/availability";
    public static final String X_API_KEY = "9YFNNKS31u9gCFKPetPWdAAjEXnED0B3K18AeYgg";
    public static final String SECRET_KEY = "2przp49a52";

    OkHttpClient client = new OkHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    private String market;
    private String language;

    public LotApiClient(String market, String language) {
        this.market = market;
        this.language = language;
    }

    public Map getConnections(String token, String origin, String[] destination, String departureDate, String returnDate, int adults) throws IOException {

        HashMap<Object, Object> input = new HashMap<Object, Object>() {{
            put("options", new HashMap<Object, Object>() {{
                put("fromCache",true);
                put("fareType", new String[] {"ALL"});
            }});
            put("params", new HashMap<Object, Object>() {{
                put("cabinClass", "E");
                put("departureDate", new String[]{departureDate});
                put("returnDate", returnDate);
                put("market", market);
                put("origin", new String[]{origin});
                put("tripType", "R");
                put("adt", adults);
                put("destination", destination );
            }});
        }};
        RequestBody body = getRequestBody(input);

        Request req = new Request.Builder()
                .url(CONNECTIONS_URL)
                .post(body)
                .addHeader("X-Api-Key", X_API_KEY)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Content-Type", "application/json")
                .build();

        Response resp = client.newCall(req).execute();

        return mapper.readValue(resp.body().string(), Map.class);

    }
    public String getToken() throws IOException {

        HashMap<Object, Object> tokenInput = new HashMap<Object, Object>() {{
            put("secret_key",SECRET_KEY);
            put("params", new HashMap<Object, Object>() {{
                put("market", market);
                put("language", language);
            }});
        }};

        RequestBody body = getRequestBody(tokenInput);
        Request tokenRequest = new Request.Builder()
                .url(GET_TOKEN_URL)
                .post(body)
                .addHeader("X-Api-Key", X_API_KEY)
                .build();

        Response response = client.newCall(tokenRequest).execute();
        String string = response.body().string();
        DocumentContext ctx = JsonPath.parse(string);

        return ctx.read("$['access_token']");
    }

    private RequestBody getRequestBody(Map data) throws JsonProcessingException {
        String json = mapper.writeValueAsString(data);
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

}
