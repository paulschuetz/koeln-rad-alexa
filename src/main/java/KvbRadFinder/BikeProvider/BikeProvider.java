package KvbRadFinder.BikeProvider;

import KvbRadFinder.Model.Bike;
import KvbRadFinder.Model.GeoLocation;

import java.util.Set;

public interface BikeProvider {

    public Set<Bike> getBikes(GeoLocation geoLocation);

}
