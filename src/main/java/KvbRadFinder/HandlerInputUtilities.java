package KvbRadFinder;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.function.Predicate;

public class HandlerInputUtilities {
    public static Predicate<HandlerInput> deviceHasDisplay(){
        return input -> input.getRequestEnvelope().getContext().getDisplay()!=null;
    }
}
