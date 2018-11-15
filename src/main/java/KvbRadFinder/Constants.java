package KvbRadFinder;

import KvbRadFinder.StaticMap.Size;

public class Constants {

    // speeches
    public static final String HELP_SPEECH = "Willkommen! Radar Köln hilft dir ein KVB Rad in deiner Nähe zu finden. Sage dazu einfach: Alexa, frage Radar Köln wo das nächste freie Fahrrad steht.";
    public static final String WELCOME_SPEECH = "Willkommen bei Radar Köln. Soll ich dir helfen ein Rad in deiner Nähe zu finden?";
    public static final String CANCEL_RESPONSE = "Okay. Bis bald!";
    public static final String UNKNOWN_INTENT_RESPONSE = "Sorry wir konnten dich leider nicht verstehen. Versuche es erneut.";
    public static final String UNKNOWN_FAILURE_RESPONSE = "Wir hatten Probleme die Anfrage zu verarbeiten. Bitte versuche es zu einem späteren Zeitpunkt erneut oder wende dich an den Support";
    public static final String INSUFFICIENT_ADDRESS_INFORMATION_RESPONSE = "Die von Ihnen eingetragenen Standortinformationen von ihrem Echo Geraet reichen nicht aus um es zu lokalisieren. Bitte ergänzen Sie die Informationen in der Alexa App um diesen Service optimal nutzen zu können";

    public static final Size SMALL_IMAGE = new Size(720, 480);
    public static final Size LARGE_IMAGE = new Size(1200, 800);

    public static final int METERS_PER_MINUTE = 50;
}
