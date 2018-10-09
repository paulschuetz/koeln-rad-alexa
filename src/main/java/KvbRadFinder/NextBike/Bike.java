package KvbRadFinder.NextBike;

import KvbRadFinder.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.xml.stream.Location;
import java.time.LocalDateTime;


@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class Bike {
    private GeoLocation geoLocation;
}
