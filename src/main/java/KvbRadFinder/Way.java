package KvbRadFinder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter @EqualsAndHashCode @ToString
public class Way{

    private GeoLocation start;
    private GeoLocation destination;
    private double distance;

    public Way(GeoLocation start, GeoLocation destination){
        this.start = start;
        this.destination = destination;
        this.distance = calculateDistance(start, destination);
    }

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
