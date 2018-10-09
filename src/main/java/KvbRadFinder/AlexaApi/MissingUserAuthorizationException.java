package KvbRadFinder.AlexaApi;

public class MissingUserAuthorizationException extends Exception {

    public MissingUserAuthorizationException(){super();}

    public MissingUserAuthorizationException(String message){
        super(message);
    }

}
