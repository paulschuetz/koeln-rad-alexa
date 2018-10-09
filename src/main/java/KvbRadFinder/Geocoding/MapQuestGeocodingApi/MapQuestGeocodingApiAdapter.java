package KvbRadFinder.Geocoding.MapQuestGeocodingApi;

import KvbRadFinder.GeoLocation;
import KvbRadFinder.Geocoding.Exceptions.AuthorizationException;
import KvbRadFinder.Geocoding.Exceptions.ExternalServiceCommunicationException;
import KvbRadFinder.Geocoding.Exceptions.InsufficientAddressInformationException;
import KvbRadFinder.Geocoding.GeocodingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static KvbRadFinder.Keys.MAPQUEST_API_KEY;

@Slf4j
public class MapQuestGeocodingApiAdapter implements GeocodingService {

    private static final String GEOCODE_ADDRESS_API = "http://www.mapquestapi.com/geocoding/v1/address";
    private static final int MAX_RESULTS = 1;

    @Override
    public GeoLocation getGeoLocation(String address) throws ExternalServiceCommunicationException, AuthorizationException, InsufficientAddressInformationException {

        GeocodeAdressApiQuery query = new GeocodeAdressApiQuery(address).withMaxResults(MAX_RESULTS);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(query.construct());
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if(response.getStatus() >= 500) throw new ExternalServiceCommunicationException();
        if(response.getStatus() == 403) throw new AuthorizationException();

        RResolvedLocation location = target.request().get(RResolvedLocation.class);
        log.info(location.toString());

        // check quality of location
        if(!location.getGeoCodeQuality().equals("POINT")) throw new InsufficientAddressInformationException();

        return new GeoLocation(location.getLatituide(), location.getLatituide());
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
