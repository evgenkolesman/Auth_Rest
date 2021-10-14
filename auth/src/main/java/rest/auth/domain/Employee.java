package rest.auth.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "INN")
    private int INN;

    @NotNull
    @Column(name = "hired")
    private Timestamp hired;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> persons;

    public static Employee of(String surname, String name, int INN) {
        Employee employee = new Employee();
        employee.surname = surname;
        employee.name = name;
        employee.INN = INN;
        employee.hired = new Timestamp(System.currentTimeMillis());
        return employee;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
