package KvbRadFinder.Geocoding;

import KvbRadFinder.Address;
import KvbRadFinder.GeoLocation;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;

public interface GeocodingService {

    GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException;
    Address getAddress(GeoLocation geoLocation) throws ExternalServiceCommunicationException, AuthorizationException;

}
