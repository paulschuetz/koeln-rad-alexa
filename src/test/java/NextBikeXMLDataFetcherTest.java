import KvbRadFinder.BikeProvider.BikeProvider;
import KvbRadFinder.Model.Bike;
import KvbRadFinder.BikeProvider.NextBike.NextBikeXMLDataFetcher;
import KvbRadFinder.Model.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class NextBikeXMLDataFetcherTest {

    @Test
    public void testXMLParsing() {
        BikeProvider fetcher = new NextBikeXMLDataFetcher();
        Collection<Bike> bikes = fetcher.getBikes(new GeoLocation(0,0));
        assertTrue(() -> bikes.size() > 0, "no bikes were returned");
    }

}
