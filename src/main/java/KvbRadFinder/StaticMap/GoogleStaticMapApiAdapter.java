package KvbRadFinder.StaticMap;

import KvbRadFinder.GeoLocation;
import com.sun.istack.internal.NotNull;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static KvbRadFinder.Keys.GOOGLE_MAPS_STATIC_API_KEY;

/**
 * @see <a href=https://developers.google.com/maps/documentation/maps-static/dev-guide>https://developers.google.com/maps/documentation/maps-static/dev-guide</a>
 */
public class GoogleStaticMapApiAdapter implements StaticMapImageCreator {

    private static final String GOOGLE_MAPS_STATIC_BASE_URL = "https://maps.googleapis.com/maps/api/staticmap";
    private static final Size DEFAULT_SIZE = new Size(400,400);

    @Override
    public URI constructMap(GeoLocation from, GeoLocation to, ImageOptions options) {
        try {
            URIBuilder uri = new URIBuilder(GOOGLE_MAPS_STATIC_BASE_URL);
            Size size = (options.getSize()==null) ? DEFAULT_SIZE : options.getSize();
            uri.addParameter("size",toGoogleSizeParameter(size));
            uri.addParameter("path",toGooglePathParameter(from,to));
            uri.addParameter("markers",toGooglePathParameter(to));
            uri.addParameter("key", GOOGLE_MAPS_STATIC_API_KEY);
            return uri.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("could not build uri for google static image api", e);
        }
    }

    private String toGooglePathParameter(@NotNull GeoLocation ...locations){
        return Arrays.stream(locations)
                .map(location -> Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude()))
                .reduce((loc1,loc2) -> loc1 + "|"+ loc2)
                .orElse("50.94731,6.89758|50.9460428,6.896832017");
    }

    private String toGoogleSizeParameter(Size size){
        return Integer.toString(size.getWidth()) + "x" + Integer.toString(size.getHeight());
    }
}