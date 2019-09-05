package address.app.repository;

import address.app.config.CassandraConfig;
import address.app.model.Address;
import address.app.model.Student;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

public class AddressRepositoryImpl<E> implements AddressRepository<E> {

    @Autowired
    private CassandraConfig cassandraConfig;


    @Value("${cassandra.keyspace.name}")
    private String keyspaceName;
    private Session session;
    private MappingManager manager;
    private Mapper<Address> mapper;


    @PostConstruct
    public void init() {
        try {
            session = cassandraConfig.getSession();
            manager = new MappingManager(session);
            mapper = manager.mapper(Address.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initiate StudentService", e);
        }
    }


    @Override
    public void create(E e) {

        mapper.save((Address) e);

    }

    @Override
    public E getbyId(UUID id) {
        return (E)mapper.get(id);
    }

    @Override
    public List<E> findAll() {
        return null;//(E)mapper.get();
    }

    @Override
    public void delete(UUID Id) {

    }
}
