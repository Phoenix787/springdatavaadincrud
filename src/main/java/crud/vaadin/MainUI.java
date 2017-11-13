package crud.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import crud.backend.Person;
import crud.backend.PersonRepository;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.components.DisclosurePanel;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.label.RichText;

@SpringUI
@Theme("valo")
@Title("Phonebook CRUD example")

public class MainUI extends UI {

    PersonRepository repository;
    EventBus.UIEventBus eventBus;
    PersonForm personForm;

    private MGrid<Person> list = new MGrid<>(Person.class)
            .withProperties("id", "name", "email")
            .withColumnHeaders("Id", "Name", "Email")
            .withFullWidth();
    private MTextField filterbyName = new MTextField().withPlaceholder("Filter by name");

    private Button addNew = new MButton(VaadinIcons.PLUS, this::add);
    private Button edit = new MButton(VaadinIcons.EDIT, this::edit);
    private Button delete = new ConfirmButton(VaadinIcons.TRASH, "Are you sure want delete the entry?", this::delete);

    public MainUI(PersonRepository repository, EventBus.UIEventBus eventBus, PersonForm personForm) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.personForm = personForm;
    }

    @Override
    protected void init(VaadinRequest request) {

        DisclosurePanel aboutBox = new DisclosurePanel("Spring boot JPA CRUD example with Vaadin UI", new RichText().withMarkDownResource("/welcome.md"));

        setContent(new MVerticalLayout(
                aboutBox,
                new MHorizontalLayout(filterbyName, addNew, edit, delete),
                list
        ).expand(list));
        listEntities();

        list.asSingleSelect().addValueChangeListener(e->adjustActionButtonState());
        filterbyName.addValueChangeListener(e -> listEntities(e.getValue()));

        eventBus.subscribe(this);

    }

    private void adjustActionButtonState() {
        boolean hasSelection = !list.getSelectedItems().isEmpty();
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    private void listEntities() {
        listEntities(filterbyName.getValue());
    }

    final int PAGESIZE = 45;

    private void listEntities(String nameFilter) {
        if (nameFilter.isEmpty()){

            list.setRows(repository.findAll());
        }else {
            String filter = "%" + nameFilter + "%";
            list.setRows(repository.findByNameLikeIgnoreCase(filter));
        }

        // Lazy binding for better optimized connection from the Vaadin Table to
        // Spring Repository. This approach uses less memory and database
        // resources. Use this approach if you expect you'll have lots of data
        // in your table. There are simpler APIs if you don't need sorting.
        //list.setDataProvider(
        //        // entity fetching strategy
        //        (sortOrder, offset, limit) -> {
        //            final List<Person> page = repo.findByNameLikeIgnoreCase(likeFilter,
        //                    new PageRequest(
        //                            offset / limit,
        //                            limit,
        //                            sortOrder.isEmpty() || sortOrder.get(0).getDirection() == SortDirection.ASCENDING ? Sort.Direction.ASC : Sort.Direction.DESC,
        //                            // fall back to id as "natural order"
        //                            sortOrder.isEmpty() ? "id" : sortOrder.get(0).getSorted()
        //                    )
        //            );
        //            return page.subList(offset % limit, page.size()).stream();
        //        },
        //        // count fetching strategy
        //        () -> (int) repo.countByNameLike(likeFilter)
        //);

        adjustActionButtonState();
    }

    public void add(Button.ClickEvent event) {
        edit(new Person());
    }

    public void edit(Button.ClickEvent e) {
        edit(list.asSingleSelect().getValue());
    }

    public void delete(){
        repository.delete(list.asSingleSelect().getValue());
        list.deselectAll();
        listEntities();
    }

    protected void edit(final Person phoneBookEntry) {
        personForm.setEntity(phoneBookEntry);
        personForm.openInModalPopup();
    }

    @EventBusListenerMethod(scope = EventScope.UI)
    public void onPersonModified(PersonModifiedEvent event) {
        listEntities();
        personForm.closePopup();
    }

}
