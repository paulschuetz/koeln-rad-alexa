package KvbRadFinder;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

import static KvbRadFinder.SpeechUtilities.replaceUmlauteWithUnicode;

public class Responses {

    public static Optional<Response> HELP_RESPONSE(HandlerInput handlerInput){
        String speech = replaceUmlauteWithUnicode("Willkommen! Radar Köln hilft dir ein KVB Rad in deiner Nähe zu finden. Sage dazu einfach: Alexa, frage Radar Köln wo das nächste freie Fahrrad steht.");

        return handlerInput.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> CANCEL_RESPONSE(HandlerInput handlerInput){
        String speech = replaceUmlauteWithUnicode("Bis Bald!");

        return handlerInput.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }

    public static Optional<Response> UNSUPPORTED_INTENT_RESPONSE(HandlerInput handlerInput){

        String speech = replaceUmlauteWithUnicode("Sorry wir konnten dich leider nicht verstehen. Versuche es erneut.");

        return handlerInput.getResponseBuilder()
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }


    public static Optional<Response> UNKNOWN_FAILURE_RESPONSE(HandlerInput input) {

        String speech = replaceUmlauteWithUnicode("Wir hatten Probleme die Anfrage zu verarbeiten. Bitte versuche es zu einem späteren Zeitpunkt erneut oder wende dich an den Support");

        return input.getResponseBuilder()
                .withSpeech(speech)
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

        String speech = replaceUmlauteWithUnicode("Du musst uns zunächst die Erlaubnis geben den Standort deines Alexa Geräts aus deinem Alexa Profil abzurufen. Öffne dazu bitte jetzt deine Alexa App.");

        return input.getResponseBuilder()
                .withAskForPermissionsConsentCard(ImmutableList.of("read::alexa:device:all:address"))
                .withSpeech(speech)
                .withShouldEndSession(true)
                .build();
    }


}
