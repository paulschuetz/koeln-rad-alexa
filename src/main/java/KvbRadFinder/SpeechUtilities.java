package KvbRadFinder;

public class SpeechUtilities {

    public static final String LINE_BREAK = "\n";

    public static String replaceUmlauteWithUnicode(String text) {
        return text.replace("Ä", "\u00c4")
                .replace("ä", "\u00e4")
                .replace("Ö", "\u00d6")
                .replace("ö", "\u00f6")
                .replace("Ü", "\u00dc")
                .replace("ü", "\u00fc")
                .replace("ß", "\u00df");
    }
}
