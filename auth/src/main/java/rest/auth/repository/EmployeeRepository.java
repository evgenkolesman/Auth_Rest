package rest.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rest.auth.domain.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
