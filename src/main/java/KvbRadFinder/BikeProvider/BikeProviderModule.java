package KvbRadFinder.BikeProvider;

import KvbRadFinder.BikeProvider.NextBike.NextBikeXMLDataFetcher;
import dagger.Binds;
import dagger.Module;

@Module
public interface BikeProviderModule {

    @Binds
    BikeProvider bikeProvider(NextBikeXMLDataFetcher nextBikeXMLDataFetcher);

}
