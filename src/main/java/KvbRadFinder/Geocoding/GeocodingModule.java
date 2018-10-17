package KvbRadFinder.Geocoding;

import KvbRadFinder.Geocoding.MapQuestGeocodingApi.MapQuestGeocodingApiAdapter;
import dagger.Binds;
import dagger.Module;

@Module
public interface GeocodingModule {
    @Binds
    GeocodingService geocodingService(MapQuestGeocodingApiAdapter mapQuestGeocodingApiAdapter);
}
