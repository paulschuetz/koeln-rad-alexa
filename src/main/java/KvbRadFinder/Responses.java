package KvbRadFinder;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

import static KvbRadFinder.Constants.UNKNOWN_FAILURE_RESPONSE;
import static KvbRadFinder.SpeechUtilities.replaceUmlauteWithUnicode;

public class Responses {

    public static Optional<Response> UNKNOWN_FAILURE_RESPONSE(HandlerInput input) {
        return input.getResponseBuilder()
                .withSpeech(UNKNOWN_FAILURE_RESPONSE)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> INSUFFICIENT_ADRESS_INFORMATION_RESPONSE(HandlerInput input) {

        String speech = replaceUmlauteWithUnicode("Die von Ihnen eingetragenen Standortinformationen von ihrem Echo Gerät reichen nicht aus um es zu lokalisieren. Bitte ergänzen Sie die Informationen in der Alexa App um diesen Service optimal nutzen zu können");

        return input.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> MISSING_USER_LOCATION_PERMISSIONS_RESPONSE(HandlerInput input){
        return input.getResponseBuilder()
                .withAskForPermissionsConsentCard(ImmutableList.of("read::alexa:device:all:address"))
                .withSpeech(replaceUmlauteWithUnicode("Du musst uns zunächst die Erlaubnis geben den Standort deines Alexa Geräts aus deinem Alexa Profil abzurufen. Öffne dazu bitte jetzt deine Alexa App."))
                .withShouldEndSession(true)
                .build();
    }


}
