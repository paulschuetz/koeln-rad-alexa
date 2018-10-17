package KvbRadFinder.AlexaApi;

import java.util.Locale;

public class CountryCodeHelper {

    public static String getCountry(String countryCode) {
        Locale locale = new Locale("German", countryCode);
        return locale.getDisplayCountry();
    }

}
