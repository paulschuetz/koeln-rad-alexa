package KvbRadFinder.Geocoding.Exceptions;

/**
 * Occurs if the provided address information was not specific enough to handle the request
 */
public class InsufficientAddressInformationException extends Exception {

    public InsufficientAddressInformationException(){super();}

    public InsufficientAddressInformationException(String message){
        super(message);
    }

}
