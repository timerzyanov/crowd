package ru.timerzyanov.crowd.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.timerzyanov.crowd.components.EmployeeEditor;
import ru.timerzyanov.crowd.domain.Employee;
import ru.timerzyanov.crowd.repo.EmployeeRepo;

@Route
public class MainView extends VerticalLayout {

    private final EmployeeRepo employeeRepo;
    private Grid<Employee> grid = new Grid<>(Employee.class);
    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);
    private final EmployeeEditor editor;

    @Autowired
    public MainView(EmployeeRepo employeeRepo, EmployeeEditor editor) {
        this.employeeRepo = employeeRepo;
        this.editor = editor;
        add(toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showEmployee(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editEmployee(new Employee()));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showEmployee(filter.getValue());
        });

        showEmployee("");
    }

    private void showEmployee(String name) {
        grid.setItems(name.isEmpty() ? employeeRepo.findAll() : employeeRepo.findByName(name));
    }
}
