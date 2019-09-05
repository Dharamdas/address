package address.app.repository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AddressRepository<E> {

    public void create(E e);
    public E getbyId(UUID Id);
    public List<E> findAll();
    public void delete(UUID Id);

}
