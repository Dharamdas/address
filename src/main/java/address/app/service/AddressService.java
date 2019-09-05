package address.app.service;

import address.app.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressService {

    public UUID createAddress(Address address);
    public Address getAddress(UUID addressId);
    public List<Address> findAll();
}
