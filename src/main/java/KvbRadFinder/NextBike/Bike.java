package KvbRadFinder.NextBike;

import KvbRadFinder.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.xml.stream.Location;
import java.time.LocalDateTime;


@EqualsAndHashCode
@AllArgsConstructor
@Getter
@ToString
public class Bike {
    private GeoLocation geoLocation;
}
