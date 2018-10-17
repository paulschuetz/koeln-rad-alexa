import KvbRadFinder.Model.GeoLocation;
import KvbRadFinder.StaticMap.GoogleStaticMapApiAdapter;
import KvbRadFinder.StaticMap.ImageOptions;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public class GoogleStaticMapApiAdapterTest {

    final static GeoLocation AUTOHAUS_DIRKES = new GeoLocation(50.94731, 6.89758);
    final static GeoLocation BIKE = new GeoLocation(50.9460428, 6.896832017);

    @Test
    public void test() {
        StaticMapImageCreator googleStaticMapApi = new GoogleStaticMapApiAdapter();
        URI uri = googleStaticMapApi.constructMap(AUTOHAUS_DIRKES, BIKE, new ImageOptions());

        SetUp.initializeUnirest();

        GetRequest get = Unirest.get(uri.toString());
        try {
            HttpResponse<InputStream> response = get.asBinary();
            assertTrue(response.getStatus() == 200, "status should be 200 but was " + response.getStatus());
        } catch (UnirestException e) {
            e.printStackTrace();
            fail();
        }

    }
}
