package KvbRadFinder;

import KvbRadFinder.RequestHandler.*;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import java.io.IOException;


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
        initializeUnirest();
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
