import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.MapQuestGeocodingApi.MapQuestGeocodingApiAdapter;
import KvbRadFinder.Model.Adress;
import KvbRadFinder.Model.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class MapQuestGeocodingApiAdapterTest {

    MapQuestGeocodingApiAdapter mapQuestApi = new MapQuestGeocodingApiAdapter();
    final static GeoLocation AUTOHAUS_DIRKES = new GeoLocation(50.94731, 6.89758);

    @Test
    public void getGeoLocation_validInput() {
        SetUp.initializeUnirest();
        try {
            GeoLocation location = mapQuestApi.getGeoLocation("Deutschland Köln 50825 Maarweg 241");
            // sollte auf mindestens 100 Meter genau sein
            int inaccuracy = location.distanceInMeter(AUTOHAUS_DIRKES);
            log.info("Inaccuracy for locating Dirkes was " + inaccuracy);
            assertTrue(inaccuracy < 100, "Inaccuracy of the geocoding request was " + inaccuracy + " but should definitely be below 100");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getAddress_validInput() {
        SetUp.initializeUnirest();
        try {
            Adress address = mapQuestApi.getAddress(AUTOHAUS_DIRKES);
            log.info("Addess: " + address.toString());
            assertTrue(address.getPostalCode().equals("50825"));
            assertTrue(address.getStreet()
                    .contains("Maarweg"));
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (ExternalServiceCommunicationException e) {
            e.printStackTrace();
        }
    }
}
