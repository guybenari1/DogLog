package superapp.miniApps.getParks;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import superapp.boundaries.InvokedByBoundary;
import superapp.boundaries.LocationBoundary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class GetParksMethods {
    public GetParksMethods() {

    }

    public Object getParksByLatLng(String lat, String lng, String myGoogleAPIKey, InvokedByBoundary invokedBy) {
        String GoogleFindPlaceAPI = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=Museum%20of%20Contemporary%20Art%20Australia&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Copening_hours%2Cgeometry&key=" + myGoogleAPIKey;
        /* Sending a Text Search GET request */
        HttpRequest getRequestObject;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse;
        String responseBody;
        String textToSerach = "dog+parks";
        String location = lat + "%2C" + lng;
        String textSearchUrl = this.buildTextSearchURL(location, myGoogleAPIKey, textToSerach);
        try {
            getRequestObject = this.createGetRequest(textSearchUrl);
            getResponse = httpClient.send(getRequestObject, BodyHandlers.ofString());
            responseBody = getResponse.body();
            // Create an instance of ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Deserialize the JSON into the ResponseData object
            GoogleMapResponse responseData = new GoogleMapResponse();
            responseData = objectMapper.readValue(responseBody, GoogleMapResponse.class);
            responseData.setInvokedBy(invokedBy);
            System.out.println(responseData);
            // Return the responseData as a JSON HTTP response
            return responseData;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle the exception and return an appropriate HTTP response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public Object getParksBySelfLocation(String myGoogleAPIKey, InvokedByBoundary invokedBy) {
        HttpRequest getRequestObject;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response;
        String responseBody;
        String ip = getIp();
        String ipGeolocationKey = "7588d4d78e124d6c8dc9fee39838bcc9";
        String getGeoLocationAPIEndPoint = "https://api.ipgeolocation.io/ipgeo?apiKey=" + ipGeolocationKey + "&ip=" + ip;
        try {
            getRequestObject = this.createGetRequest(getGeoLocationAPIEndPoint);
            response = httpClient.send(getRequestObject, BodyHandlers.ofString());
            responseBody = response.body();
            // Create an instance of ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Deserialize the JON into the ResponseData object
            IPRequestResult ipRequestRes;
            ipRequestRes = objectMapper.readValue(responseBody, IPRequestResult.class);
            String lng = ipRequestRes.getLongitude();
            String lat = ipRequestRes.getLatitude();
            return this.getParksByLatLng(lat, lng, myGoogleAPIKey, invokedBy);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
            // Handle the exception and return an appropriate HTTP response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String getIp() {
        try {
            URL url = new URL("https://api.ipify.org");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String ipAddress = reader.readLine();
            System.out.println("Your IP address is: " + ipAddress);
            reader.close();
            return ipAddress;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpRequest createGetRequest(String textSearchUrl) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(textSearchUrl))
                .header("MediaType", "text/plain")
                .GET()
                .build();
    }

    public String buildTextSearchURL(String location, String googleApiKey, String textToSerach) {
        int radius = 150;
        return "https://maps.googleapis.com/maps/api/place/textsearch/json?"
                + "location=" + location
                + "&radius=" + radius
                + "&key=" + googleApiKey
                + "&query=" + textToSerach;
    }

    public LocationBoundary getLocationInMap(Map<String, Object> commandAttributes) {
        if (!commandAttributes.containsKey("lat") && !commandAttributes.containsKey("lng"))
            throw new RuntimeException("commandEntity Attributes do not contain Location values");
        float lat = ((Double) commandAttributes.get("lat")).floatValue();
        float lng = ((Double) commandAttributes.get("lng")).floatValue();
        return new LocationBoundary(lat, lng);
    }
}
