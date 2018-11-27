package KvbRadFinder.RequestHandler;

import KvbRadFinder.AlexaApi.AlexaAddressApiAdapter;
import KvbRadFinder.AlexaApi.InsufficientAddressInformationException;
import KvbRadFinder.AlexaApi.MissingUserAuthorizationException;
import KvbRadFinder.AlexaApi.RequestFailedException;
import KvbRadFinder.BikeProvider.BikeProvider;
import KvbRadFinder.Constants;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.GeocodingService;
import KvbRadFinder.Model.Adress;
import KvbRadFinder.Model.Bike;
import KvbRadFinder.Model.GeoLocation;
import KvbRadFinder.Model.Way;
import KvbRadFinder.StaticMap.ImageOptions;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.display.BodyTemplate7;
import com.amazon.ask.model.interfaces.display.ImageInstance;
import com.amazon.ask.model.interfaces.display.Template;
import com.amazon.ask.model.ui.Image;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static KvbRadFinder.Constants.LARGE_IMAGE;
import static KvbRadFinder.Constants.SMALL_IMAGE;
import static KvbRadFinder.HandlerInputUtilities.deviceHasDisplay;
import static KvbRadFinder.Responses.*;
import static KvbRadFinder.Responses.INSUFFICIENT_ADRESS_INFORMATION_RESPONSE;
import static KvbRadFinder.Responses.UNKNOWN_FAILURE_RESPONSE;
import static KvbRadFinder.SpeechUtilities.LINE_BREAK;
import static KvbRadFinder.SpeechUtilities.replaceUmlauteWithUnicode;
import static com.amazon.ask.request.Predicates.intentName;
import static com.amazon.ask.request.Predicates.requestType;
import static com.amazon.ask.request.Predicates.sessionAttribute;

@Slf4j
@SuppressWarnings("Duplicates")
public class LaunchIntentHandler implements RequestHandler {


    @Inject
    LaunchIntentHandler() {
    }

    @Override
    public boolean canHandle(HandlerInput input) {

        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        String speech = replaceUmlauteWithUnicode("<speak> Willkommen bei Radar Köln. Wir helfen dir bequem das nächste freie Fahrrad in deiner Nähe zu finden. Sage dazu einfach Folgendes: <break strength=\"strong\" time=\"1s\"/> Alexa, frage Radar Köln wo das nächste freie Fahrrad steht </speak>");

        return handlerInput.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }
}
