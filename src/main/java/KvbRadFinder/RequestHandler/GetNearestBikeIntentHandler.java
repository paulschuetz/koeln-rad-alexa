package KvbRadFinder.RequestHandler;

import KvbRadFinder.Address;
import KvbRadFinder.AlexaApi.*;
import KvbRadFinder.GeoLocation;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.GeocodingService;
import KvbRadFinder.Geocoding.MapQuestGeocodingApi.MapQuestGeocodingApiAdapter;
import KvbRadFinder.NextBike.Bike;
import KvbRadFinder.NextBike.NextBikeDataFetcher;
import KvbRadFinder.NextBike.NextBikeXMLDataFetcher;
import KvbRadFinder.StaticMap.GoogleStaticMapApiAdapter;
import KvbRadFinder.StaticMap.ImageOptions;
import KvbRadFinder.StaticMap.SpeechUtilities;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import KvbRadFinder.Way;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.display.BodyTemplate7;
import com.amazon.ask.model.interfaces.display.ImageInstance;
import com.amazon.ask.model.interfaces.display.Template;
import com.amazon.ask.model.ui.Image;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static KvbRadFinder.Constants.*;
import static KvbRadFinder.HandlerInputUtilities.deviceHasDisplay;
import static KvbRadFinder.Responses.INSUFFICIENT_ADDRESS_INFORMATION;
import static KvbRadFinder.Responses.UNKNOWN_FAILURE;
import static KvbRadFinder.StaticMap.SpeechUtilities.LINE_BREAK;
import static KvbRadFinder.StaticMap.SpeechUtilities.replaceUmlauteWithUnicode;
import static com.amazon.ask.request.Predicates.*;

@Slf4j
public class GetNearestBikeIntentHandler implements RequestHandler{

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("GetNearestBikeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String apiAccessToken = handlerInput.getRequestEnvelope().getContext().getSystem().getApiAccessToken();
        String apiEndpoint = handlerInput.getRequestEnvelope().getContext().getSystem().getApiEndpoint();
        String deviceId = handlerInput.getRequestEnvelope().getContext().getSystem().getDevice().getDeviceId();

        AlexaAddressApiAdapter alexaAddressApi = new AlexaAddressApiAdapter();
        Address userAddress;
        try {
            userAddress = alexaAddressApi.getUserLocation(apiAccessToken,deviceId,apiEndpoint);
            System.out.println("User Location: " + userAddress.toString());
        } catch (MissingUserAuthorizationException e) {
            return handlerInput.getResponseBuilder()
                    .withAskForPermissionsConsentCard(ImmutableList.of("read::alexa:device:all:address"))
                    .withSpeech("Du musst uns zunaechst die Erlaubnis geben den Standort deines Alexa Geraets aus deinem Alexa Profil abzurufen. Oeffne dazu bitte jetzt deine Alexa App.")
                    .build();

        } catch (InsufficientAddressInformationException e) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Die Addressinformationen deines Echo Geraets sind nicht vollstaendig oder fehlerhaft. Bitte öffne deine Alexa App und aktualisiere die hinterlegten Informationen.")
                    .withShouldEndSession(true)
                    .build();
        } catch (RequestFailedException e) {
            e.printStackTrace();
            return handlerInput.getResponseBuilder()
                    .withSpeech(UNKNOWN_FAILURE)
                    .withShouldEndSession(true)
                    .build();
        }
        // if we reached this line we have got the user address
        // now get location for that address:
        GeocodingService geocodingService = new MapQuestGeocodingApiAdapter();
        GeoLocation userLocation;
        try {
            userLocation = geocodingService.getGeoLocation(userAddress.getAsSearchString());
        } catch (ExternalServiceCommunicationException | AuthorizationException e) {
            return UNKNOWN_FAILURE(handlerInput);
        } catch (KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException e) {
            return INSUFFICIENT_ADDRESS_INFORMATION(handlerInput);
        }
        // okay jetzt werden die Räder gefetcht
        NextBikeDataFetcher dataFetcher = new NextBikeXMLDataFetcher();
        Set<Bike> bikes = dataFetcher.getBikes();
        Set<GeoLocation> bikeLocations = bikes.stream().map(Bike::getGeoLocation).collect(Collectors.toSet());

        // get nearest bike
        Way nearestBike = userLocation.nearest(bikeLocations);
        // get street name for geolocation
        Address bikeAddress;
        try {
            bikeAddress = geocodingService.getAddress(nearestBike.destination());
        } catch (ExternalServiceCommunicationException | AuthorizationException e) {
            return UNKNOWN_FAILURE(handlerInput);
        }

        // now get the image
        StaticMapImageCreator staticMapImageCreator = new GoogleStaticMapApiAdapter();
        URI smallImage = staticMapImageCreator.constructMap(userLocation, nearestBike.destination(), new ImageOptions().withSize(SMALL_IMAGE));
        URI largeImage = staticMapImageCreator.constructMap(userLocation, nearestBike.destination(), new ImageOptions().withSize(LARGE_IMAGE));

        Image image = Image.builder().withSmallImageUrl(smallImage.toString()).withLargeImageUrl(largeImage.toString()).build();

        String speech = replaceUmlauteWithUnicode("Das nächste freie Fahrrad ist " +  nearestBike.durationAsSpeech() + " entfernt. " +
                "Schau in deine Alexa App um mehr über den Standort des Fahrrades zu erfahren.");

        final String cardText = nearestBikeCardText(nearestBike, bikeAddress);
        final String cardTitle = replaceUmlauteWithUnicode("Wir haben ein Rad in der Nähe gefunden!");

        if(handlerInput.matches(deviceHasDisplay())){
            Template mapScreen = buildMapScreen(smallImage, largeImage);

            return handlerInput.getResponseBuilder()
                    .withSpeech(speech)
                    .withStandardCard(cardTitle, cardText, image)
                    .addRenderTemplateDirective(mapScreen)
                    .withShouldEndSession(true)
                    .build();
        }
        else{
            return handlerInput.getResponseBuilder()
                    .withSpeech(speech)
                    .withStandardCard(cardTitle, cardText, image)
                    .withShouldEndSession(true)
                    .build();
        }
    }

    private Template buildMapScreen(URI smallImage, URI largeImage){
        ImageInstance smallImageInstance = ImageInstance.builder().withHeightPixels(SMALL_IMAGE.getHeight()).withWidthPixels(SMALL_IMAGE.getHeight()).withUrl(smallImage.toString()).build();
        ImageInstance largeImageInstance = ImageInstance.builder().withHeightPixels(LARGE_IMAGE.getHeight()).withWidthPixels(LARGE_IMAGE.getHeight()).withUrl(largeImage.toString()).build();
        com.amazon.ask.model.interfaces.display.Image image
                = com.amazon.ask.model.interfaces.display.Image.builder().withSources(Lists.newArrayList(smallImageInstance,largeImageInstance)).build();
        return BodyTemplate7.builder().withBackgroundImage(image).build();
    }

    private String nearestBikeCardText(Way bike, Address bikeAddress){
        String cardText = "" +
                "Adresse: " + bikeAddress.getStreet() + LINE_BREAK +
                "Entfernung: ~" + bike.distanceAsSpeech() + LINE_BREAK +
                "Dauer: ~" + bike.durationAsSpeech();

        return replaceUmlauteWithUnicode(cardText);
    }
}
