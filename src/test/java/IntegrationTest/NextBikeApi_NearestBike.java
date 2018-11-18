package IntegrationTest;

import KvbRadFinder.BikeProvider.BikeProvider;
import KvbRadFinder.Model.Bike;
import KvbRadFinder.Model.GeoLocation;
import KvbRadFinder.Model.Way;
import KvbRadFinder.BikeProvider.NextBike.NextBikeXMLDataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class NextBikeApi_NearestBike {

    final static GeoLocation AUTOHAUS_DIRKES = new GeoLocation(50.94731, 6.89758);

    @Test
    public void getNearestKvbBike() {
        BikeProvider nextBikeApi = new NextBikeXMLDataFetcher();
        Set<Bike> bikes = nextBikeApi.getBikes(new GeoLocation(0, 0));
        Set<GeoLocation> bikeLocations = bikes.stream()
                .map(Bike::getGeoLocation)
                .collect(Collectors.toSet());

        Way nearest = AUTOHAUS_DIRKES.nearest(bikeLocations);

        log.debug("nearest bike: " + nearest.toString());

    }

}
