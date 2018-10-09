import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;
import KvbRadFinder.Geocoding.MapQuestGeocodingApi.MapQuestGeocodingApiAdapter;
import org.junit.Test;

public class MapQuestGeocodingApiAdapterTest {

    MapQuestGeocodingApiAdapter mapQuestApi = new MapQuestGeocodingApiAdapter();

    @Test
    public void getGeoLocationTest_validRequest(){
        try {
            mapQuestApi.getGeoLocation("Deutschland Köln 50825 Maarweg 241");
        } catch (ExternalServiceCommunicationException e) {
            e.printStackTrace();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        } catch (InsufficientAddressInformationException e) {
            e.printStackTrace();
        }
    }
}
