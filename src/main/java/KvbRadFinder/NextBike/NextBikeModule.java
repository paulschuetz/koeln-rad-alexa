package KvbRadFinder.NextBike;

import dagger.Binds;
import dagger.Module;

@Module
public interface NextBikeModule {

    @Binds
    NextBikeDataFetcher nextBikeDataFetcher(NextBikeXMLDataFetcher nextBikeXMLDataFetcher);

}
