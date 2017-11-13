package crud;

import crud.backend.Person;
import crud.backend.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication
public class Application {

    @Bean
    public CommandLineRunner loadDate(PersonRepository repository) {
        return (args)->{
            repository.save(new Person("Elmo Herring", new GregorianCalendar(1991, 0, 20).getTime(),"dolor.Donec@enimconsequatpurus.net" ));
            repository.save(new Person("Malcolm Blankenship", new GregorianCalendar(1993, 5, 29).getTime(),"eu.placerat.eget@lacusQuisque.com" ));
            repository.save(new Person("Jack Prince", new GregorianCalendar(1991, 5, 11).getTime(),"sodales@loremluctusut.edu" ));
            repository.save(new Person("Hamilton Ortiz", new GregorianCalendar(1987, 5, 14).getTime(),"Aliquam@nec.com" ));
            repository.save(new Person("Amir Rocha", new GregorianCalendar(1987, 10, 30).getTime(),"Nunc.pulvinar@facilisisSuspendissecommodo.co.uk" ));
            repository.save(new Person("Tanner Chambers", new GregorianCalendar(1988, 6, 5).getTime(),"ligula@lacusQuisque.net" ));

//            INSERT INTO person (id,name,birth_day,email) VALUES(7,'Randall Day','1990-02-17','nisi@consectetuercursus.ca');
//            INSERT INTO person (id,name,birth_day,email) VALUES(8,'Vladimir Estes','1987-04-10','sed.sapien.Nunc@estcongue.net');
//            INSERT INTO person (id,name,birth_day,email) VALUES(9,'Hakeem Lambert','1994-01-26','Sed.congue@temporaugue.net');
//            INSERT INTO person (id,name,birth_day,email) VALUES(10,'Dale Todd','1994-08-17','enim@gravida.org');
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
