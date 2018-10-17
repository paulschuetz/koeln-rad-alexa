package KvbRadFinder.StaticMap;

import KvbRadFinder.Model.GeoLocation;
import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import static KvbRadFinder.Keys.GOOGLE_MAPS_STATIC_API_KEY;

/**
 * @see <a href=https://developers.google.com/maps/documentation/maps-static/dev-guide>https://developers.google.com/maps/documentation/maps-static/dev-guide</a>
 */
public class GoogleStaticMapApiAdapter implements StaticMapImageCreator {

    @Inject
    public GoogleStaticMapApiAdapter() {
    }

    private static final String GOOGLE_MAPS_STATIC_BASE_URL = "https://maps.googleapis.com/maps/api/staticmap";
    private static final Size DEFAULT_SIZE = new Size(400, 400);

    @Override
    public URI constructMap(GeoLocation from, GeoLocation to, ImageOptions options) {
        try {
            URIBuilder uriBuilder = new URIBuilder(GOOGLE_MAPS_STATIC_BASE_URL);
            Size size = (options.getSize() == null) ? DEFAULT_SIZE : options.getSize();
            uriBuilder.addParameter("size", toGoogleSizeParameter(size));
            uriBuilder.addParameter("path", toGooglePathParameter(from, to));
            uriBuilder.addParameter("markers", toGooglePathParameter(to));
            uriBuilder.addParameter("key", GOOGLE_MAPS_STATIC_API_KEY);
            URI uri = uriBuilder.build();
            System.out.println("constructed new map: " + uri.toString());
            return uri;
        } catch (URISyntaxException e) {
            throw new RuntimeException("could not build uri for google static image api", e);
        }
    }

    private String toGooglePathParameter(@NotNull GeoLocation... locations) {
        return Arrays.stream(locations)
                .map(location -> Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude()))
                .reduce((loc1, loc2) -> loc1 + "|" + loc2)
                .orElse("50.94731,6.89758|50.9460428,6.896832017");
    }

    private String toGoogleSizeParameter(Size size) {
        return Integer.toString(size.getWidth()) + "x" + Integer.toString(size.getHeight());
    }
}
