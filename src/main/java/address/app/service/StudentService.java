package address.app.service;

import address.app.config.CassandraConfig;
import address.app.model.Student;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Service
public class StudentService {

    @Autowired
    private CassandraConfig cassandraConfig;

    @Value("${cassandra.keyspace.name}")
    private String keyspaceName;
    private Session session;
    private MappingManager manager;
    private Mapper<Student> mapper;


    @PostConstruct
    public void init() {
        try {
            session = cassandraConfig.getSession();
            manager = new MappingManager(session);
            mapper = manager.mapper(Student.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initiate StudentService", e);
        }
    }


    public UUID createStudent(Student student) throws Exception {
        student.setStudentId(UUID.randomUUID());
        mapper.save(student);
        return student.getStudentId();
    }

    public void delete(UUID studentId) throws Exception {
        Student student = getStudentById(studentId).one();
        mapper.delete(student);
    }


    public Result < Student > getStudentById(UUID studentId) throws Exception {
        Result< Student > result = null;
        Statement statement = QueryBuilder
                .select()
                .from(keyspaceName, "student")
                .where(eq("student_id", studentId)).setFetchSize(10);
       // statement.setConsistencyLevel(cassandraConnector.getConsistencyLevel());
        try {
            System.out.println("session object: "+session);
            ResultSet resultSet = session.execute(statement);
            result = mapper.map(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to search Student for studentId :" + studentId.toString(), e);
        }
        return result;
    }

}
