package KvbRadFinder.Geocoding.MapQuestGeocodingApi;

import KvbRadFinder.AlexaApi.CountryCodeHelper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Map;

@Getter @NoArgsConstructor @AllArgsConstructor @JsonIgnoreProperties(ignoreUnknown = true) @ToString @Slf4j
public class RResolvedAddress {
    String city;
    int postalCode;
    String street;
    String country;

    @JsonProperty("results")
    public void getNestedProperties(Object[] results){
        Map<String, Object> result = (Map<String,Object>) results[0];
        ArrayList<Object> locations = (ArrayList) result.get("locations");
        Map<String, Object> location =  (Map<String, Object>) locations.get(0);

        String postalCode = (String) location.get("postalCode");
        if(postalCode.length()==5){
            this.postalCode = Integer.parseInt(postalCode);
            this.postalCode=50825;
        }

        this.city = (String) location.get("adminArea5");

        String streetInfo = (String) location.get("street");
        // swag street-number and street-name
        this.street = swapStreetNameAndStreetNumber(streetInfo);

        String countryCode = (String) location.get("adminArea1");
        this.country = CountryCodeHelper.getCountry(countryCode);
        this.country = "Deutschland";
    }

    // MapQuest sends street info in format: [HouseNumber] [StreetName] -> "241 Maarweg" but we want "Maarweg 241"
    private String swapStreetNameAndStreetNumber(String streetInfo){
        // check if houseNumber is present
        String[] numberAndName = streetInfo.split(" ");
        if(numberAndName.length==1) return numberAndName[0];
        if(numberAndName.length==2) return numberAndName[1] + " " + numberAndName[0];
        else return streetInfo;
    }
}
