package ru.demo.vaadindemo.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import lombok.Setter;
import ru.demo.vaadindemo.dto.EmployeeDto;

/**
 * EmployeeEditor.
 *
 * @author Pavel_Zhitlov
 */
public class EmployeeEditor extends Composite<VerticalLayout> {

    private EmployeeDto employeeDto;

    @Setter
    private SaveListener saveListener;
    @Setter
    private DeleteListener deleteListener;
    @Setter
    private CancelListener cancelListener;

    private final Binder<EmployeeDto> binder = new BeanValidationBinder<>(EmployeeDto.class);

    public EmployeeEditor() {
        TextField nameField = new TextField("name");
        TextField phoneField = new TextField("phone");

        Button save = new Button("Save", VaadinIcon.CHECK.create());
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete", VaadinIcon.TRASH.create());

        binder.forField(nameField).bind("name");
        binder.forField(phoneField).bind("phone");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(e -> save());
        save.addClickShortcut(Key.ENTER);

        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(e -> deleteListener.onDelete(employeeDto));

        cancel.addClickListener(e -> cancelListener.onCancel());

        getContent().add(nameField, phoneField, new HorizontalLayout(save, cancel, delete));
    }

    private void save() {
        // Save the form into a new instance of Employee
        EmployeeDto newEmployeeDto = new EmployeeDto();
        newEmployeeDto.setId(employeeDto.getId());

        if (binder.writeBeanIfValid(newEmployeeDto)) {
            saveListener.onSave(newEmployeeDto);
        }
    }

    public void setEmployee(EmployeeDto employeeDto) {
        this.employeeDto = employeeDto;
        binder.readBean(employeeDto);
    }

    public interface SaveListener {
        void onSave(EmployeeDto employeeDto);
    }

    public interface DeleteListener {
        void onDelete(EmployeeDto employeeDto);
    }

    public interface CancelListener {
        void onCancel();
    }
}