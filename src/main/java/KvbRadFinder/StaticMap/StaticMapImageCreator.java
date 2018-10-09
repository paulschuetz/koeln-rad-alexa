package KvbRadFinder.StaticMap;

import KvbRadFinder.GeoLocation;

import java.net.URI;

public interface StaticMapImageCreator {
    /**
     * Builds an Image of a map showing the way from point A to point B and provides the corresponding url
     * @return the HTTPS image url to the image
     */
    URI constructMap(GeoLocation from, GeoLocation to, ImageOptions options);
}
