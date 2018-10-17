import KvbRadFinder.AlexaApi.CountryCodeHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryCodeHelperTest {
    @Test
    public void test() {
        String country = CountryCodeHelper.getCountry("DE");
        assertEquals(country, "Deutschland");
    }
}
