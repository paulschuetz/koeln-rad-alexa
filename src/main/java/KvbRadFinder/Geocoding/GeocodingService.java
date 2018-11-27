package KvbRadFinder.Geocoding;

import KvbRadFinder.AlexaApi.InsufficientAddressInformationException;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Model.Adress;
import KvbRadFinder.Model.GeoLocation;

public interface GeocodingService {

    GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException;

    Adress getAddress(GeoLocation geoLocation) throws ExternalServiceCommunicationException, AuthorizationException;

}
