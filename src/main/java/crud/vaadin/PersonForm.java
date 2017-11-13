package crud.vaadin;

import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import crud.backend.Person;
import crud.backend.PersonRepository;
import org.vaadin.spring.events.EventBus;
import org.vaadin.teemu.switchui.Switch;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@SpringComponent
@UIScope

public class PersonForm extends AbstractForm<Person> {

    EventBus.UIEventBus eventBus;
    PersonRepository repository;

    TextField name = new MTextField("Name");
    TextField email = new MTextField("Email");
    TextField phoneNumber = new MTextField("Phone");
    DateField birthDate = new DateField("Birth date");
    Switch colleague = new Switch("Colleague");

    public PersonForm(PersonRepository r, EventBus.UIEventBus eventBus) {
        super(Person.class);
        this.repository = r;
        this.eventBus = eventBus;

        // On save & cancel, publish events that other parts of the UI can listen
        setSavedHandler(person -> {
            repository.save(person);
            eventBus.publish(this, new PersonModifiedEvent(person));
        });

        setResetHandler(p -> eventBus.publish(this, new PersonModifiedEvent(p)));
        setSizeUndefined();
    }

    @Override
    protected void bind() {
        // DateField in Vaadin 8 uses LocalDate by default, the backend
        // uses plain old java.util.Date, thus we need a converter, using
        // built in helper here
        getBinder().forMemberField(birthDate).withConverter(new LocalDateToDateConverter());
        super.bind();
    }

    @Override
    protected Component createContent() {
        return new MVerticalLayout(
                new MFormLayout(
                        name,
                        email,
                        phoneNumber,
                        birthDate,
                        colleague
                ).withWidth(""), getToolbar()
        ).withWidth("");
    }
}
