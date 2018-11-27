package KvbRadFinder.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ResolvedBikeLocation {
    private Way wayToBike;
    private Adress bikeAdress;
}
