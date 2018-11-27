package KvbRadFinder.Model;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
public class Adress {
    String city;
    String postalCode;
    String street;
    String country;

    public Adress(String country, String city, String postalCode, String street) {
        if (street == null) throw new IllegalArgumentException("street was null");
        if (city == null && postalCode == null) throw new IllegalArgumentException("missing postal-code and city");

        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
    }

    public Adress withCountry(String country) {
        this.country = country;
        return this;
    }

    public Adress withCity(String city) {
        this.city = city;
        return this;
    }

    public Adress withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }


    public String getAsSearchString() {
        String searchString = "";
        if (country != null) searchString += " " + country;
        if (postalCode != null) searchString += " " + postalCode;
        if (city != null) searchString += " " + city;
        if (street != null) searchString += " " + street;
        return searchString;
    }

}
