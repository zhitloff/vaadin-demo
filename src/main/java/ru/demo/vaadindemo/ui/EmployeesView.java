package ru.demo.vaadindemo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import ru.demo.vaadindemo.dto.EmployeeDto;
import ru.demo.vaadindemo.service.EmployeeService;
import ru.demo.vaadindemo.ui.component.EmployeeEditor;

/**
 * EmployeesView.
 *
 * @author Pavel_Zhitlov
 */
@Route("")
public class EmployeesView extends VerticalLayout {
    private final EmployeeService employeeService;

    private final TextField filter;
    private final Grid<EmployeeDto> grid;
    private final EmployeeEditor editor;


    public EmployeesView(EmployeeService employeeService) {
        this.employeeService = employeeService;

        // Create components
        Button addButton = new Button("New employee", VaadinIcon.PLUS.create());
        filter = new TextField();
        grid = new Grid<>(EmployeeDto.class);
        editor = new EmployeeEditor();

        // Configure components
        configureEditor();

        addButton.addClickListener(e -> editEmployee(new EmployeeDto()));

        filter.setPlaceholder("Filter by last name");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateEmployees(e.getValue()));

        grid.setHeight("200px");
        grid.asSingleSelect().addValueChangeListener(e -> editEmployee(e.getValue()));

        // Compose layout
        HorizontalLayout actionsLayout = new HorizontalLayout(filter, addButton);
        add(actionsLayout, grid, editor);

        // List customers
        updateEmployees("");
    }

    private void configureEditor() {
        editor.setVisible(false);

        editor.setSaveListener(employeeDto -> {
            var savedEmployeeDto = employeeService.saveEmployee(employeeDto);
            updateEmployees(filter.getValue());
            editor.setEmployee(null);
            grid.asSingleSelect().setValue(savedEmployeeDto);
        });

        editor.setDeleteListener(employeeDto -> {
            employeeService.deleteEmployee(employeeDto);
            updateEmployees(filter.getValue());
            editEmployee(null);
        });

        editor.setCancelListener(() -> editEmployee(null));
    }

    private void editEmployee(EmployeeDto employeeDto) {
        editor.setEmployee(employeeDto);
        if (employeeDto != null) {
            editor.setVisible(true);
        } else {
            // Deselect grid
            grid.asSingleSelect().setValue(null);
            editor.setVisible(false);
        }

    }

    private void updateEmployees(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(employeeService.getEmployees());
        } else {
            grid.setItems(employeeService.getEmployeeListByName(filterText));
        }
    }
}
