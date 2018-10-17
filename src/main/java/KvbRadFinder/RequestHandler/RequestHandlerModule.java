package KvbRadFinder.RequestHandler;

import KvbRadFinder.AlexaApi.AlexaAddressApiAdapter;
import KvbRadFinder.Geocoding.GeocodingModule;
import KvbRadFinder.Geocoding.GeocodingService;
import KvbRadFinder.NextBike.NextBikeDataFetcher;
import KvbRadFinder.NextBike.NextBikeModule;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import KvbRadFinder.StaticMap.StaticMapModule;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = {GeocodingModule.class, NextBikeModule.class, StaticMapModule.class})
public class RequestHandlerModule {

    @Provides
    @IntoSet
    RequestHandler cancelAndStopIntentHandler(CancelAndStopIntentHandler cancelAndStopIntentHandler) {
        return cancelAndStopIntentHandler;
    }

    @Provides
    @IntoSet
    RequestHandler fallBackIntentHandler(FallbackIntentHandler fallBackIntentHandler) {
        return fallBackIntentHandler;
    }

    @Provides
    @IntoSet
    RequestHandler getNearestBikeIntentHandler(GeocodingService geocodingService, NextBikeDataFetcher nextBikeDataFetcher, StaticMapImageCreator staticMapImageCreator, AlexaAddressApiAdapter alexaAddressApiAdapter) {
        return new GetNearestBikeIntentHandler(geocodingService, alexaAddressApiAdapter, nextBikeDataFetcher, staticMapImageCreator);
    }

    @Provides
    @IntoSet
    RequestHandler helpIntentHandler(HelpIntentHandler helpIntentHandler) {
        return helpIntentHandler;
    }

    @Provides
    @IntoSet
    RequestHandler launchIntentHandler(LaunchIntentHandler launchIntentHandler) {
        return launchIntentHandler;
    }

    @Provides
    @IntoSet
    RequestHandler welcomeIntentHandler(WelcomeIntentHandler welcomeIntentHandler) {
        return welcomeIntentHandler;
    }


}
