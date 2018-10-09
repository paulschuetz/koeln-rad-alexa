package KvbRadFinder.RequestHandler;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class WelcomeIntentHandler implements RequestHandler{

    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("WelcomeIntent"));
    }

    public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech("Hallo mein Freund")
                .withSimpleCard("Willkommen bei KVB RAD FINDER","Wir helfen Ihnen möglichst bequem das nächste freie Rad zu finden")
                .build();
    }
}
