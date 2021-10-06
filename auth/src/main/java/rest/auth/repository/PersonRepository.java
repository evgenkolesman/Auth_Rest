package rest.auth.repository;

import org.springframework.data.repository.CrudRepository;
import rest.auth.domain.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
