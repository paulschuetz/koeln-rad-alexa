package KvbRadFinder.AlexaApi;

public class RequestFailedException extends Exception{

    public RequestFailedException(){super();}

    public RequestFailedException(String message){
        super(message);
    }

    public RequestFailedException(String message, Throwable t){
        super(message, t);
    }

}
