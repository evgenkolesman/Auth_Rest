package rest.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import rest.auth.AuthApplicationTests;
import rest.auth.domain.Employee;
import rest.auth.domain.Person;
import rest.auth.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplicationTests.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    EmployeeRepository repo;
    @MockBean
    private RestTemplate rest;

    private static final String API = "http://localhost:8080/person/";

    @Test
    void findAllTest() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = Employee.of("Vasya", "p1", 122);
        employee.setId(1L);
        employee.setPersons(Collections.singletonList(new Person()));
        when(repo.findAll()).thenReturn(List.of(employee));
        String req = mapper.writer().writeValueAsString(employee);
        this.mvc.perform(get("/employee/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = Person.of("Vasya", "p1");
        person.setId(1);
        String req = mapper.writer().writeValueAsString(person);
        when(rest.postForObject(API, person, Person.class)).thenReturn(person);
        this.mvc.perform(post("/employee/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = Person.of("Vasya", "p1");
        person.setId(1);
        String req = mapper.writer().writeValueAsString(person);
        when(rest.postForObject(API, person, Person.class)).thenReturn(person);
        this.mvc.perform(put("/employee/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteTest() throws Exception{
        this.mvc.perform(delete("/employee/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}