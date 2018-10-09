package KvbRadFinder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter @AllArgsConstructor @EqualsAndHashCode @ToString
public class GeoLocation {
    private double latitude;
    private double longitude;

    public Way nearest(Set<GeoLocation> geoLocations){
        Way wayToNearestBike = geoLocations.stream().map(geoLocation -> new Way(this, geoLocation)).reduce((nearest,other)->{
            if(nearest.getDistance() < other.getDistance()) return nearest;
            return other;
        }).orElse(new Way(new GeoLocation(0,0),new GeoLocation(0,0)));

        System.out.println("Way to nearest bike: " + wayToNearestBike.toString());

        return wayToNearestBike;
    }
}
