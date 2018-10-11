package KvbRadFinder.Geocoding.MapQuestGeocodingApi;

import KvbRadFinder.GeoLocation;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;
import KvbRadFinder.Geocoding.GeocodingService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

import static KvbRadFinder.Keys.MAPQUEST_API_KEY;

@Slf4j
public class MapQuestGeocodingApiAdapter implements GeocodingService {

    private static final String GEOCODE_ADDRESS_API = "http://www.mapquestapi.com/geocoding/v1/address";
    private static final int MAX_RESULTS = 1;

    @Override
    public GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException {


        GeocodeAdressApiQuery query = new GeocodeAdressApiQuery(address).withMaxResults(MAX_RESULTS);
        GetRequest get = Unirest.get(query.construct()).header("Accept", MediaType.APPLICATION_JSON);

        HttpResponse<RResolvedLocation> response;
        try {
            response = get.asObject(RResolvedLocation.class);
        } catch (UnirestException e) {
            e.printStackTrace();
            throw new ExternalServiceCommunicationException();
        }

        if(response.getStatus() >= 500) throw new ExternalServiceCommunicationException();
        if(response.getStatus() == 403) throw new AuthorizationException();

        RResolvedLocation location = response.getBody();

        // check quality of location
        String geoCodeQuality = location.getGeoCodeQuality();
        if(!geoCodeQuality.equals("POINT"))
            throw new InsufficientAddressInformationException("getCodeQuality for " + address + " was " + geoCodeQuality + " but expected was POINT");

        return new GeoLocation(location.getLatitude(), location.getLongitude());
    }

    @Override
    public String getAdress(GeoLocation geoLocation) {
        return null;
    }

    private class GeocodeAdressApiQuery{

        private String address;
        private int maxResults;

        GeocodeAdressApiQuery(String address){
            this.address = address;
        }

        GeocodeAdressApiQuery withMaxResults(int maxResults){
            this.maxResults = maxResults;
            return this;
        }

        String construct(){
            try {
                URIBuilder builder = new URIBuilder(GEOCODE_ADDRESS_API);
                builder.addParameter("key", MAPQUEST_API_KEY);
                builder.addParameter("location", address);
                builder.addParameter("outFormat", "json");
                if (maxResults > 0) builder.addParameter("maxResults", Integer.toString(maxResults));
                return builder.toString();
            }
            // this should never happen because new URIBuilder(GEOCODE_ADDRESS_API) will always stay the same
            catch (URISyntaxException e){
                log.error("Could not build URI for MapQuest API call", e);
                throw new RuntimeException("abort mission");
            }
        }

    }
}
