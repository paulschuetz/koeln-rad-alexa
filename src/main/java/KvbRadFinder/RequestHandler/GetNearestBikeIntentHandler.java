package KvbRadFinder.RequestHandler;

import KvbRadFinder.AlexaApi.AlexaAddressApiAdapter;
import KvbRadFinder.AlexaApi.InsufficientAddressInformationException;
import KvbRadFinder.AlexaApi.MissingUserAuthorizationException;
import KvbRadFinder.AlexaApi.RequestFailedException;
import KvbRadFinder.BikeProvider.BikeProvider;
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
import static KvbRadFinder.Responses.INSUFFICIENT_ADRESS_INFORMATION_RESPONSE;
import static KvbRadFinder.Responses.MISSING_USER_LOCATION_PERMISSIONS_RESPONSE;
import static KvbRadFinder.Responses.UNKNOWN_FAILURE_RESPONSE;
import static KvbRadFinder.SpeechUtilities.LINE_BREAK;
import static KvbRadFinder.SpeechUtilities.replaceUmlauteWithUnicode;
import static com.amazon.ask.request.Predicates.intentName;

@SuppressWarnings("Duplicates")
@Slf4j
public class GetNearestBikeIntentHandler implements RequestHandler {

    private GeocodingService geocodingService;
    private AlexaAddressApiAdapter alexaAddressApi;
    private BikeProvider bikeProvider;
    private StaticMapImageCreator staticMapImageCreator;

    @Inject
    GetNearestBikeIntentHandler(GeocodingService geocodingService, AlexaAddressApiAdapter alexaAddressApiAdapter, BikeProvider bikeProvider, StaticMapImageCreator staticMapImageCreator) {
        this.geocodingService = geocodingService;
        this.alexaAddressApi = alexaAddressApiAdapter;
        this.bikeProvider = bikeProvider;
        this.staticMapImageCreator = staticMapImageCreator;
    }

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("GetNearestBikeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String apiAccessToken = handlerInput.getRequestEnvelope()
                .getContext()
                .getSystem()
                .getApiAccessToken();
        String apiEndpoint = handlerInput.getRequestEnvelope()
                .getContext()
                .getSystem()
                .getApiEndpoint();
        String deviceId = handlerInput.getRequestEnvelope()
                .getContext()
                .getSystem()
                .getDevice()
                .getDeviceId();

        Adress userAddress;
        try {
            userAddress = alexaAddressApi.getUserLocation(apiAccessToken, deviceId, apiEndpoint);
            System.out.println("User Location: " + userAddress.toString());
        } catch (MissingUserAuthorizationException e) {
            return MISSING_USER_LOCATION_PERMISSIONS_RESPONSE(handlerInput);
        } catch (InsufficientAddressInformationException e) {
            return INSUFFICIENT_ADRESS_INFORMATION_RESPONSE(handlerInput);
        } catch (RequestFailedException e) {
            e.printStackTrace();
            return UNKNOWN_FAILURE_RESPONSE(handlerInput);
        }
        // if we reached this line we have got the user address
        // now get location for that address:
        GeoLocation userLocation;
        try {
            userLocation = geocodingService.getGeoLocation(userAddress.getAsSearchString());
        } catch (ExternalServiceCommunicationException | AuthorizationException e) {
            return UNKNOWN_FAILURE_RESPONSE(handlerInput);
        } catch (InsufficientAddressInformationException e) {
            return INSUFFICIENT_ADRESS_INFORMATION_RESPONSE(handlerInput);
        }
        // okay jetzt werden die Räder gefetcht
        Set<Bike> bikes = bikeProvider.getBikes(userLocation);
        Set<GeoLocation> bikeLocations = bikes.stream()
                .map(Bike::getGeoLocation)
                .collect(Collectors.toSet());

        // get nearestWay bike
        Way nearestBike = userLocation.nearestWay(bikeLocations);
        // get street name for geolocation
        Adress bikeAddress;
        try {
            bikeAddress = geocodingService.getAddress(nearestBike.destination());
        } catch (ExternalServiceCommunicationException | AuthorizationException e) {
            return UNKNOWN_FAILURE_RESPONSE(handlerInput);
        }

        // now get the image
        URI smallImage = staticMapImageCreator.constructMap(userLocation, nearestBike.destination(), new ImageOptions().withSize(SMALL_IMAGE));
        URI largeImage = staticMapImageCreator.constructMap(userLocation, nearestBike.destination(), new ImageOptions().withSize(LARGE_IMAGE));

        Image image = Image.builder()
                .withSmallImageUrl(smallImage.toString())
                .withLargeImageUrl(largeImage.toString())
                .build();

        String speech = replaceUmlauteWithUnicode("Das nächste freie Fahrrad ist " + nearestBike.durationAsSpeech() + " entfernt. " +
                "Schau in deine Alexa App um mehr über den Standort des Fahrrades zu erfahren.");

        final String cardText = nearestBikeCardText(nearestBike, bikeAddress);
        final String cardTitle = replaceUmlauteWithUnicode("Wir haben ein Rad in der Nähe gefunden!");

        if (handlerInput.matches(deviceHasDisplay())) {
            Template mapScreen = buildMapScreen(smallImage, largeImage);

            return handlerInput.getResponseBuilder()
                    .withSpeech(speech)
                    .withStandardCard(cardTitle, cardText, image)
                    .addRenderTemplateDirective(mapScreen)
                    .withShouldEndSession(true)
                    .build();
        } else {
            return handlerInput.getResponseBuilder()
                    .withSpeech(speech)
                    .withStandardCard(cardTitle, cardText, image)
                    .withShouldEndSession(true)
                    .build();
        }
    }

    private Template buildMapScreen(URI smallImage, URI largeImage) {
        ImageInstance smallImageInstance = ImageInstance.builder()
                .withHeightPixels(SMALL_IMAGE.getHeight())
                .withWidthPixels(SMALL_IMAGE.getHeight())
                .withUrl(smallImage.toString())
                .build();
        ImageInstance largeImageInstance = ImageInstance.builder()
                .withHeightPixels(LARGE_IMAGE.getHeight())
                .withWidthPixels(LARGE_IMAGE.getHeight())
                .withUrl(largeImage.toString())
                .build();
        com.amazon.ask.model.interfaces.display.Image image
                = com.amazon.ask.model.interfaces.display.Image.builder()
                .withSources(Lists.newArrayList(smallImageInstance, largeImageInstance))
                .build();
        return BodyTemplate7.builder()
                .withBackgroundImage(image)
                .build();
    }

    private String nearestBikeCardText(Way bike, Adress bikeAddress) {
        String cardText = "" +
                "Adresse: " + bikeAddress.getStreet() + LINE_BREAK +
                "Entfernung: ~" + bike.distanceAsSpeech() + LINE_BREAK +
                "Dauer: ~" + bike.durationAsSpeech();

        return replaceUmlauteWithUnicode(cardText);
    }
}
