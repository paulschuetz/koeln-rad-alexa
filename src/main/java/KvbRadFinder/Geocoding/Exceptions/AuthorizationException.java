package KvbRadFinder.Geocoding.Exceptions;

/**
 * Occurs if the external API is not able to authorize our authority (mostly if it returns 403) -> refresh access tokens
 */
public class AuthorizationException extends Exception {
}
