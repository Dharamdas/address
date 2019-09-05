package address.app.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(keyspace = "test01",name = "address")
public class Address {

    @PartitionKey
    @Column(name = "addressid")
    private UUID addressid;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name="pincode")
    private String pincode;
    @Column(name = "locality")
    private String locality;

    public UUID getAddressid() {
        return addressid;
    }

    public void setAddressid(UUID addressid) {
        this.addressid = addressid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
}
