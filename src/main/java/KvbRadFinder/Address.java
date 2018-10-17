package KvbRadFinder;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
public class Address {
    String city;
    int postalCode;
    String street;
    String country;

    public Address(String country, String city, int postalCode, String street){
        if(street==null) throw new IllegalArgumentException("street was null");
        if(city==null && postalCode==0) throw new IllegalArgumentException("missing postalcode/city");

        this.country=country;
        this.city=city;
        this.postalCode=postalCode;
        this.street=street;
    }

    public Address(@NotNull String street, @NotNull String city){
        if(street ==null || city ==null) throw new IllegalArgumentException("street or city can not be null");
        this.street = street;
        this.city= city;
    }

    public Address(@NotNull String street, @NotNull int postalCode){
        if(postalCode == 0 || city ==null) throw new IllegalArgumentException("street or city can not be null");
        this.street = street;
        this.postalCode= postalCode;
    }

    public Address withCountry(String country){
        this.country=country;
        return this;
    }

    public Address withCity(String city){
        this.city=city;
        return this;
    }

    public Address withPostalCode(int postalCode){
        this.postalCode = postalCode;
        return this;
    }


    public String getAsSearchString(){
        String searchString = "";
        if(country!=null) searchString+= " " +country;
        if(postalCode!=0) searchString+= " " + Integer.toString(postalCode);
        if(city!=null) searchString+= " " + city;
        if(street!=null) searchString += " " + street;
        return searchString;
    }

}
