package KvbRadFinder.AlexaApi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;

import javax.ws.rs.core.MediaType;

public class AlexaAddressApiAdapter {

    private static final String apiAdressPath = "/v1/devices/*deviceId*/settings/address";

    public UserAddress getUserLocation(String apiToken, String deviceId, String apiEndpoint) throws MissingUserAuthorizationException, RequestFailedException, InsufficientAddressInformationException {
        String url = apiEndpoint + apiAdressPath.replace("*deviceId*", deviceId);
        System.out.println("Get the location of the user: " + url);
        try {
            HttpResponse<JsonNode> response = Unirest.get(url)
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Accept", MediaType.APPLICATION_JSON)
                    .asJson();

            System.out.println("body: " + response.getBody().toString());

            if (response.getStatus() == HttpStatus.SC_FORBIDDEN)
                throw new MissingUserAuthorizationException("user has not accepted to give street details yet");
            if (response.getStatus() != HttpStatus.SC_OK)
                throw new RequestFailedException("status code was " + response.getStatus());
            // get response
            String city = response.getBody()
                    .getObject()
                    .getString("city");
            int postalCode = response.getBody()
                    .getObject()
                    .getInt("postalCode");
            String address = response.getBody()
                    .getObject()
                    .getString("addressLine1");
            String countryCode = response.getBody()
                    .getObject()
                    .getString("countryCode");
            String country = CountryCodeHelper.getCountry(countryCode);

            System.out.println("city: " + city + "postalCode: " + postalCode + " address: " + address +  " country " +  country);

            if(address==null) throw new InsufficientAddressInformationException();
            if(postalCode==0 && city==null) throw new InsufficientAddressInformationException();
            UserAddress location;
            if(postalCode==0) location= new UserAddress(address, city).withCountry(country);
            else location= new UserAddress(address, postalCode).withCountry(country);
            return location;
        }catch (UnirestException e){
            throw new RequestFailedException(e.getMessage(), e);
        }catch (IllegalArgumentException e) {
            throw new InsufficientAddressInformationException();
        }
    }
}
