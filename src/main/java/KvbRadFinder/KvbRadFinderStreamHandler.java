package KvbRadFinder;

import KvbRadFinder.RequestHandler.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;


public class KvbRadFinderStreamHandler extends SkillStreamHandler {

    @Inject
    public KvbRadFinderStreamHandler() {
        super(getSkill());
        initializeUnirest();
    }

    private static Skill getSkill(){
        return Skills.standard()
                .addRequestHandlers(
                        new CancelAndStopIntentHandler(),
                        new FallbackIntentHandler(),
                        new GetNearestBikeIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchIntentHandler(),
                        new WelcomeIntentHandler()
                )
                .build();
    }

    private void initializeUnirest() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
