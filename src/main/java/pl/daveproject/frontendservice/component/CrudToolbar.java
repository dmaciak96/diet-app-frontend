package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CrudToolbar extends HorizontalLayout {
    private final Button addButton;
    private final Button editButton;
    private final Button deleteButton;

    public CrudToolbar() {
        this.setMargin(false);
        this.addButton = new Button(getTranslation("crud-toolbar.button-label-new"),
                new Icon(VaadinIcon.PLUS));
        this.editButton = new Button(getTranslation("crud-toolbar.button-label-edit"),
                new Icon(VaadinIcon.EDIT));
        this.deleteButton = new Button(getTranslation("crud-toolbar.button-label-delete"),
                new Icon(VaadinIcon.TRASH));
        this.editButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
        add(addButton, editButton, deleteButton);
        this.addClassNames(LumoUtility.Margin.MEDIUM);
    }

    public void addOnClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        addButton.addClickListener(listener);
    }

    public void editOnClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        editButton.addClickListener(listener);
    }

    public void deleteOnClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        deleteButton.addClickListener(listener);
    }

    public void setEnabledOnEdit(boolean enabled) {
        this.editButton.setEnabled(enabled);
    }

    public void setEnabledOnDelete(boolean enabled) {
        this.deleteButton.setEnabled(enabled);
    }
}
