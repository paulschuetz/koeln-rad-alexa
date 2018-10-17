package KvbRadFinder.Model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GeoLocation {
    private double latitude;
    private double longitude;

    public Way nearest(Set<GeoLocation> geoLocations) {
        Way wayToNearestBike = geoLocations.stream()
                .map(geoLocation -> new Way(this, geoLocation))
                .reduce((nearest, other) -> {
                    if (nearest.distance() < other.distance()) return nearest;
                    return other;
                })
                .orElse(new Way(new GeoLocation(0, 0), new GeoLocation(0, 0)));

        System.out.println("Way to nearest bike: " + wayToNearestBike.toString());

        return wayToNearestBike;
    }

    public int distanceInMeter(GeoLocation location) {
        return (int) calculateDistance(this, location);
    }

    @SuppressWarnings("Duplicates")
    private double calculateDistance(GeoLocation loc1, GeoLocation loc2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(loc2.getLatitude() - loc1.getLatitude());
        double dLng = Math.toRadians(loc2.getLongitude() - loc1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(loc1.getLatitude())) * Math.cos(Math.toRadians(loc2.getLatitude())) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (earthRadius * c);
    }
}
