import KvbRadFinder.GeoLocation;
import KvbRadFinder.StaticMap.GoogleStaticMapApiAdapter;
import KvbRadFinder.StaticMap.ImageOptions;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class GoogleStaticMapApiAdapterTest {

    final static GeoLocation AUTOHAUS_DIRKES = new GeoLocation(50.94731,6.89758);
    final static GeoLocation BIKE = new GeoLocation(50.9460428,6.896832017);

    @Test
    public void test(){
        StaticMapImageCreator googleStaticMapApi = new GoogleStaticMapApiAdapter();
        URI uri = googleStaticMapApi.constructMap(AUTOHAUS_DIRKES, BIKE, new ImageOptions());

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri.toString());
        Response response = target.request().get();
        assertTrue(response.getStatus()==200);
    }
}
