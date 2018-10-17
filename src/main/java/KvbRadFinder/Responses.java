package KvbRadFinder;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static KvbRadFinder.Constants.UNKNOWN_FAILURE;

public class Responses {

    public static Optional<Response> UNKNOWN_FAILURE(HandlerInput input){
        return input.getResponseBuilder()
                .withSpeech(UNKNOWN_FAILURE)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> INSUFFICIENT_ADDRESS_INFORMATION(HandlerInput input){
        return input.getResponseBuilder()
                .withSpeech("Die von Ihnen eingetragenen Standortinformationen von ihrem Echo Geraet reichen nicht aus um es zu lokalisieren. Bitte ergänzen Sie die Informationen um dieses Service nutzen zu können")
                .withShouldEndSession(true)
                .build();
    }



}
