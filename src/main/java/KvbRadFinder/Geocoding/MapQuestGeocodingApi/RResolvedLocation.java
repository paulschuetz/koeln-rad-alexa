package KvbRadFinder.Geocoding.MapQuestGeocodingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class RResolvedLocation {
    private String geoCodeQuality;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lng")
    private double longitude;

    @JsonProperty("results")
    private void getNestedProperties(Object[] results) {
        Map<String, Object> result = (Map<String, Object>) results[0];
        ArrayList<Object> locations = (ArrayList) result.get("locations");
        Map<String, Object> location = (Map<String, Object>) locations.get(0);
        this.geoCodeQuality = (String) location.get("geocodeQuality");
        Map<String, Double> latlng = (Map<String, Double>) location.get("latLng");
        this.latitude = (Double) latlng.get("lat")
                .doubleValue();
        this.longitude = (Double) latlng.get("lng")
                .doubleValue();
    }
}
