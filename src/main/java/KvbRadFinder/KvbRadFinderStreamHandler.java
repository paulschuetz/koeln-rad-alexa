package KvbRadFinder;

import KvbRadFinder.RequestHandler.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;



public class KvbRadFinderStreamHandler extends SkillStreamHandler {

    private static Skill getSkill(){
        return Skills.standard()
                .addRequestHandlers(
                        new WelcomeIntentHandler(),
                        new LaunchIntentHandler(),
                        new HelpIntentHandler(),
                        new CancelAndStopIntentHandler(),
                        new FallbackIntentHandler(),
                        new GetNearestBikeIntentHandler()
                )
                .build();
    }

    public KvbRadFinderStreamHandler() {
        super(getSkill());
    }
}
