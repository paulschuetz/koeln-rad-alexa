package KvbRadFinder.BikeProvider.NextBike;

import KvbRadFinder.BikeProvider.BikeProvider;
import KvbRadFinder.Model.Bike;
import KvbRadFinder.Model.GeoLocation;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class NextBikeXMLDataFetcher implements BikeProvider {

    @Inject
    public NextBikeXMLDataFetcher() {
    }

    private static final String NEXTBIKE_BIKE_LOCATIONS_COLOGNE_URL = "https://api.nextbike.net/maps/nextbike-live.xml?city=14";

    @Override
    public Set<Bike> getBikes(GeoLocation geoLocation) {
        Set<Bike> bikes = new HashSet<>();
        try {
            log.info("getBikes()");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new URL(NEXTBIKE_BIKE_LOCATIONS_COLOGNE_URL).openStream());
            document.getDocumentElement()
                    .normalize();

            NodeList list = document.getElementsByTagName("place");

            for (int i = 0; i < list.getLength(); i++) {
                Node location = list.item(i);
                double longitude = Double.parseDouble(location.getAttributes()
                        .getNamedItem("lng")
                        .getNodeValue());
                double latitude = Double.parseDouble(location.getAttributes()
                        .getNamedItem("lat")
                        .getNodeValue());
                bikes.add(new Bike(new GeoLocation(latitude, longitude)));
            }
        } catch (Exception e) {
            return bikes;
        }
        return bikes;
    }


}
