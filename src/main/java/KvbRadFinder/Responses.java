package KvbRadFinder;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static KvbRadFinder.Constants.INSUFFICIENT_ADDRESS_INFORMATION_RESPONSE;
import static KvbRadFinder.Constants.UNKNOWN_FAILURE_RESPONSE;

public class Responses {

    public static Optional<Response> UNKNOWN_FAILURE_RESPONSE(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(UNKNOWN_FAILURE_RESPONSE)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> INSUFFICIENT_ADDRESS_INFORMATION_RESPONSE(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(INSUFFICIENT_ADDRESS_INFORMATION_RESPONSE)
                .withShouldEndSession(true)
                .build();
    }


}
