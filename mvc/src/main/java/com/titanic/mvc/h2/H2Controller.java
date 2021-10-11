package com.titanic.mvc.h2;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/h2")
@RequiredArgsConstructor
public class H2Controller {

    private final PersonRepository personRepository;

    @GetMapping("/save")
    public String save() {
        personRepository.save(new Person("name"));

        return "success";
    }
}

@Repository
interface PersonRepository extends JpaRepository<Person, Long> {

}

@Entity
@NoArgsConstructor
class Person {

    @Id
    private Long id;
    private String name;

    public Person(String name) {
        this.name = name;
    }
}
