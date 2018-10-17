package KvbRadFinder.RequestHandler;

import KvbRadFinder.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import javax.inject.Inject;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class CancelAndStopIntentHandler implements RequestHandler {

    @Inject
    public CancelAndStopIntentHandler() {
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AMAZON.CancelIntent").or(intentName("AMAZON.StopIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(Constants.CANCEL_RESPONSE)
                .withShouldEndSession(true)
                .build();
    }
}
