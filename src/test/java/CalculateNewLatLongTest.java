import org.junit.jupiter.api.Test;

public class CalculateNewLatLongTest {

    private static double EARTH_RADIUS = 6371007.176;
    private static double PI = 3.14159265359;

    @Test
    public void test(){
        double longitude = 6.928044;
        double latitude = 50.930435;

        double distX = 121.47378943;
        double distY = 31.22923696;

        double new_latitude  = latitude  + (distY / EARTH_RADIUS) * (180 / PI);
        double new_longitude = longitude + (distX / EARTH_RADIUS) * (180 / PI) / Math.cos(latitude * PI/180);

        System.out.println("new latitude: " +  new_latitude + " new longitude: " + new_longitude);
    }

}
