package crud.vaadin;

import crud.backend.Person;
import org.springframework.stereotype.Service;

import java.io.Serializable;

public class PersonModifiedEvent implements Serializable {
    private Person person;

    public PersonModifiedEvent(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
