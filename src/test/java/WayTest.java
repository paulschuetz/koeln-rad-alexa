import KvbRadFinder.GeoLocation;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WayTest {

    @Test
    public void getNearestTest(){

        GeoLocation start = new GeoLocation(10,10);

        GeoLocation bike2 = new GeoLocation(12,12);
        GeoLocation bike1 = new GeoLocation(12,10);
        GeoLocation bike3 = new GeoLocation(12,6);
        GeoLocation bike4 = new GeoLocation(10,10);

        Set<GeoLocation> bikes = Sets.newHashSet(bike1,bike2,bike3,bike4);

        assertTrue(start.nearest(bikes).start()==start);
        assertTrue(start.nearest(bikes).destination()==bike4);
    }
}
