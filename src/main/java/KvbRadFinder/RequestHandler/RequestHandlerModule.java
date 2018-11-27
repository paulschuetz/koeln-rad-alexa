package KvbRadFinder.RequestHandler;

import KvbRadFinder.AlexaApi.AlexaAddressApiAdapter;
import KvbRadFinder.BikeProvider.BikeProvider;
import KvbRadFinder.Geocoding.GeocodingModule;
import KvbRadFinder.Geocoding.GeocodingService;
import KvbRadFinder.BikeProvider.BikeProviderModule;
import KvbRadFinder.StaticMap.StaticMapImageCreator;
import KvbRadFinder.StaticMap.StaticMapModule;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module(includes = {GeocodingModule.class, BikeProviderModule.class, StaticMapModule.class})
public class RequestHandlerModule {

    @Provides
    @IntoSet
    RequestHandler cancelAndStopIntentHandler() {
        return new FallbackIntentHandler();
    }

    @Provides
    @IntoSet
    RequestHandler fallBackIntentHandler() {
        return new FallbackIntentHandler();
    }

    @Provides
    @IntoSet
    RequestHandler getNearestBikeIntentHandler(GeocodingService geocodingService, BikeProvider bikeProvider, StaticMapImageCreator staticMapImageCreator, AlexaAddressApiAdapter alexaAddressApiAdapter) {
        return new GetNearestBikeIntentHandler(geocodingService, alexaAddressApiAdapter, bikeProvider, staticMapImageCreator);
    }

    @Provides
    @IntoSet
    RequestHandler helpIntentHandler() {
        return new HelpIntentHandler();
    }

    @Provides
    @IntoSet
    RequestHandler launchIntentHandler(){
        return new LaunchIntentHandler();
    }

}
