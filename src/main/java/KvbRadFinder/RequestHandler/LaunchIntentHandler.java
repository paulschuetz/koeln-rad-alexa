package KvbRadFinder.RequestHandler;

import KvbRadFinder.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import javax.inject.Inject;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchIntentHandler implements RequestHandler {

    @Inject
    public LaunchIntentHandler() {
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(Constants.WELCOME_SPEECH)
                .withShouldEndSession(false)
                .build();
    }
}
