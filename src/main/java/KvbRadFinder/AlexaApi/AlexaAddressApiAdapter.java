package KvbRadFinder.AlexaApi;

import KvbRadFinder.Model.Address;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

public class AlexaAddressApiAdapter {

    @Inject
    public AlexaAddressApiAdapter() {
    }

    private static final String apiAdressPath = "/v1/devices/*deviceId*/settings/address";

    public Address getUserLocation(String apiToken, String deviceId, String apiEndpoint) throws MissingUserAuthorizationException, RequestFailedException, InsufficientAddressInformationException {
        String url = apiEndpoint + apiAdressPath.replace("*deviceId*", deviceId);
        System.out.println("Get the location of the user: " + url);
        try {
            HttpResponse<JsonNode> response = Unirest.get(url)
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Accept", MediaType.APPLICATION_JSON)
                    .asJson();

            System.out.println("body: " + response.getBody()
                    .toString());

            if (response.getStatus() == HttpStatus.SC_FORBIDDEN)
                throw new MissingUserAuthorizationException("user has not accepted to give street details yet");
            if (response.getStatus() != HttpStatus.SC_OK)
                throw new RequestFailedException("status code was " + response.getStatus());

            Address userAddress = createAddress(response);
            return userAddress;

        } catch (UnirestException e) {
            throw new RequestFailedException(e.getMessage(), e);
        }
    }

    private Address createAddress(HttpResponse<JsonNode> response) throws InsufficientAddressInformationException {
        String city = null;
        if (!response.getBody()
                .getObject()
                .isNull("city"))
            city = response.getBody()
                    .getObject()
                    .getString("city");

        int postalCode = response.getBody()
                .getObject()
                .getInt("postalCode");

        String address = null;
        if (!response.getBody()
                .getObject()
                .isNull("addressLine1"))
            address = response.getBody()
                    .getObject()
                    .getString("addressLine1");
        if (!response.getBody()
                .getObject()
                .isNull("addressLine2")) {
            if (address == null) address = response.getBody()
                    .getObject()
                    .getString("addressLine2");
            else address += "," + response.getBody()
                    .getObject()
                    .getString("addressLine2");
        }

        String country = null;
        if (!response.getBody()
                .getObject()
                .isNull("countryCode")) {
            String countryCode = response.getBody()
                    .getObject()
                    .getString("countryCode");
            country = CountryCodeHelper.getCountry(countryCode);
        }

        try {
            return new Address(country, city, postalCode, address);
        } catch (IllegalArgumentException e) {
            throw new InsufficientAddressInformationException();
        }
    }
}
