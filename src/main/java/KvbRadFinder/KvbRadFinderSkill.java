package KvbRadFinder;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Set;

@Slf4j
public class KvbRadFinderSkill {

    private Set<RequestHandler> requestHandlers;

    @Inject
    public KvbRadFinderSkill(Set<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }


    public Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(new ArrayList<>(this.requestHandlers))
                .build();
    }

}
