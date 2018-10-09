package KvbRadFinder.Geocoding;

import KvbRadFinder.GeoLocation;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;

public interface GeocodingService {

    GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException;
    String getAdress(GeoLocation geoLocation);

}
