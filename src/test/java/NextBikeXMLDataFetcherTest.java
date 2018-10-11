import KvbRadFinder.NextBike.Bike;
import KvbRadFinder.NextBike.NextBikeDataFetcher;
import KvbRadFinder.NextBike.NextBikeXMLDataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class NextBikeXMLDataFetcherTest {

    @Test
    public void testXMLParsing(){
        NextBikeDataFetcher fetcher = new NextBikeXMLDataFetcher();
        Collection<Bike> bikes = fetcher.getBikes();
        assertTrue(()->bikes.size()>0, "no bikes were returned");
    }

}
