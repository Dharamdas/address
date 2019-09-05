package address.app.service;

import address.app.model.Address;
import address.app.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public abstract class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    public UUID createAddress(Address address){
        address.setAddressid(UUID.randomUUID());
        addressRepository.create(address);
        return address.getAddressid();
    }

    public Address getAddress(UUID addressId){
        return (Address) addressRepository.getbyId(addressId);

    }
    public List<Address> findAll(){
        return addressRepository.findAll();
    }

}
