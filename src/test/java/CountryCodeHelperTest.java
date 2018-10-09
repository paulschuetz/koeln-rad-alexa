import KvbRadFinder.AlexaApi.CountryCodeHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryCodeHelperTest {
    @Test
    public void test(){
        String country = CountryCodeHelper.getCountry("DE");
        assertEquals(country,"Deutschland");
    }
}
