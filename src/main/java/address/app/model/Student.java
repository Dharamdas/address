package address.app.model;


import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Objects;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.util.UUID;

@Table(keyspace = "test01", name = "student")
public class Student {

    @PartitionKey
    @Column(name = "student_id")
    private UUID studentId;

    @ClusteringColumn
    @Column(name = "department_id")
    private Integer departmentId;

    private String name;
    private String address;

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;

        Student that = (Student) other;
        return Objects.equal(studentId, that.studentId) &&
                Objects.equal(departmentId, that.departmentId) &&
                Objects.equal(name, that.name) && Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studentId, departmentId, name, address);
    }
}
