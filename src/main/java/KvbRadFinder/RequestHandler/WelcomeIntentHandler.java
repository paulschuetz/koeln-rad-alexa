package KvbRadFinder.RequestHandler;

import KvbRadFinder.Constants;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import javax.inject.Inject;
import java.util.Optional;

import static KvbRadFinder.Constants.WELCOME_SPEECH;
import static com.amazon.ask.request.Predicates.intentName;

public class WelcomeIntentHandler implements RequestHandler {

    @Inject
    public WelcomeIntentHandler() {
    }

    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("WelcomeIntent"));
    }

    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(WELCOME_SPEECH)
                .withSimpleCard("Willkommen bei Radar Köln", "Wir helfen Ihnen möglichst bequem das nächste freie Rad zu finden")
                .build();
    }
}
