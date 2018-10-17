package KvbRadFinder.Geocoding;

import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;
import KvbRadFinder.Model.Address;
import KvbRadFinder.Model.GeoLocation;

public interface GeocodingService {

    GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException;

    Address getAddress(GeoLocation geoLocation) throws ExternalServiceCommunicationException, AuthorizationException;

}
