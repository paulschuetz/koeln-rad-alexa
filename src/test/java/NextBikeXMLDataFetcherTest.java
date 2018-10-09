import KvbRadFinder.NextBike.Bike;
import KvbRadFinder.NextBike.NextBikeDataFetcher;
import KvbRadFinder.NextBike.NextBikeXMLDataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertTrue;

@Slf4j
public class NextBikeXMLDataFetcherTest {

    @Test
    public void testXMLParsing(){
        NextBikeDataFetcher fetcher = new NextBikeXMLDataFetcher();
        Collection<Bike> bikes = fetcher.getBikes();
        assertTrue("no bikes were returned", bikes.size()>0);
    }

}
