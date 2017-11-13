package crud.backend;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;
    @NotNull(message = "name is requiered")
    @Size(min = 3, max = 50, message = "Name must be longer 3 and less then 50 characters")
    private String name;

    private Boolean colleague;

    private String phoneName;
    @NotNull(message = "Email is requiered")
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Must be valid email")
    private String email;

    public Person() {
    }

    public Person(String name, Date birthDate, String email) {
        this.birthDate = birthDate;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getColleague() {
        return colleague;
    }

    public void setColleague(Boolean colleague) {
        this.colleague = colleague;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
