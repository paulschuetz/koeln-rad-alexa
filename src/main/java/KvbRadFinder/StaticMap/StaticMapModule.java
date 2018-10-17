package KvbRadFinder.StaticMap;

import dagger.Binds;
import dagger.Module;

@Module
public interface StaticMapModule {
    @Binds
    StaticMapImageCreator staticMapImageCreator(GoogleStaticMapApiAdapter googleStaticMapApiAdapter);
}
