package KvbRadFinder;

import KvbRadFinder.RequestHandler.RequestHandlerModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {RequestHandlerModule.class})
public interface SkillComponent {
    KvbRadFinderSkill skill();
}