package rest.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rest.auth.domain.Employee;
import rest.auth.domain.Person;
import rest.auth.repository.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RestTemplate rest;

    private final EmployeeRepository repo;

    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    public EmployeeController(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
