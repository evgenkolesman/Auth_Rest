package rest.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rest.auth.AuthApplicationTests;
import rest.auth.domain.Person;
import rest.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * Тесты
 *
 */
@SpringBootTest(classes = AuthApplicationTests.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    PersonRepository repo;

    @Test
    void findAll() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var person = Person.of("Vasya", "p1");
        person.setId(1);
        when(repo.findAll()).thenReturn(List.of(person));
        String req = mapper.writer().writeValueAsString(person);
        this.mvc.perform(get("/person/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void findByIdTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var person = Person.of("Vasya", "p1");
        person.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(person));
        String req = mapper.writer().writeValueAsString(person);
        String content = this.mvc.perform(get("/person/1").contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andReturn().getRequest().getContentAsString();
        assertThat(req, is(content));
    }

    @Test
    void findByIdTestFalse() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var person = Person.of("Vasya", "p1");
        person.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(person));
        String req = mapper.writer().writeValueAsString(person);
        this.mvc.perform(get("/person/2").contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andExpect(status().is4xxClientError()).andReturn().getRequest().getContentAsString();
    }

    @Test
    void createTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = Person.of("Vasya", "p1");
        person.setId(1);
        String req = mapper.writer().writeValueAsString(person);
        this.mvc.perform(post("/person/").contentType("application/json")
                        .content(req))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(repo).save(argument.capture());
        assertThat(argument.getValue().getLogin(), is("Vasya"));
        assertThat(argument.getValue().getId(), is(1));
    }

    @Test
    void updateTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Person person = Person.of("Vasya", "p1");
        person.setId(1);
        when(repo.save(new Person())).thenReturn(person);
        String req = mapper.writer().writeValueAsString(person);
        this.mvc.perform(put("/person/").contentType(MediaType.APPLICATION_JSON)
                        .content(req))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        this.mvc.perform(delete("/person/0"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}