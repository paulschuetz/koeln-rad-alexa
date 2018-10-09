package KvbRadFinder.RequestHandler;

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
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import KvbRadFinder.Way;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.Image;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static KvbRadFinder.Constants.LARGE_IMAGE;
import static KvbRadFinder.Constants.SMALL_IMAGE;
import static KvbRadFinder.Constants.UNKNOWN_FAILURE;
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

        System.out.println("apiAccessToken: " + apiAccessToken + " apiEndpoint: " + apiEndpoint + " deviceId: " + deviceId);

        AlexaAddressApiAdapter alexaAddressApi = new AlexaAddressApiAdapter();
        UserAddress userAddress;
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
                    .withSpeech("Die Addressinformationen die du zu deinem Echo Geraet hinterlegt hast reichen nicht aus um den Standort zu identifizieren. Um trotzdem diesen Skill nutzen zu koennen oeffne die Alexa App und aktualisiere die Standort Daten zu deinen Echo-Geraeten.")
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
            return handlerInput.getResponseBuilder()
                    .withSpeech(UNKNOWN_FAILURE)
                    .withShouldEndSession(true)
                    .build();
        } catch (KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException e) {
            return handlerInput.getResponseBuilder()
                    .withSpeech("Die von Ihnen eingetragenen Standortinformationen von ihrem Echo Geraet reichen nicht aus um es zu lokalisieren. Bitte ergänzen Sie die Informationen um dieses Service nutzen zu können")
                    .withShouldEndSession(true)
                    .build();
        }
        // okay jetzt werden die Räder gefetcht
        NextBikeDataFetcher dataFetcher = new NextBikeXMLDataFetcher();
        Set<GeoLocation> bikeLocations = dataFetcher.getBikes().stream().map(Bike::getGeoLocation).collect(Collectors.toSet());

        // get nearest bike
        Way nearestBike = userLocation.nearest(bikeLocations);

        // now get the image
        StaticMapImageCreator staticMapImageCreator = new GoogleStaticMapApiAdapter();
        URI smallImage = staticMapImageCreator.constructMap(userLocation,nearestBike.getDestination(), new ImageOptions().withSize(SMALL_IMAGE));
        URI largeImage = staticMapImageCreator.constructMap(userLocation,nearestBike.getDestination(), new ImageOptions().withSize(LARGE_IMAGE));

        Image image = Image.builder().withSmallImageUrl(smallImage.toString()).withLargeImageUrl(largeImage.toString()).build();
        return handlerInput.getResponseBuilder()
                .withSpeech("Das naechste Fahrrad ist " +  (int) nearestBike.getDistance() + " Meter entfernt. Schau mal in deine Alexa App!")
                .withShouldEndSession(true)
                .build();
    }
}
