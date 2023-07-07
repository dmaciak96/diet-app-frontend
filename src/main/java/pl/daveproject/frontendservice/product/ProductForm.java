package pl.daveproject.frontendservice.product;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import pl.daveproject.frontendservice.product.model.Product;
import pl.daveproject.frontendservice.product.model.ProductType;

public class ProductForm extends FormLayout {
    private final Binder<Product> binder;
    private final TextField name;
    private final NumberField kcal;
    private final ComboBox<ProductType> type;

    public ProductForm(Product product) {
        this.name = new TextField(getTranslation("product-form.name-label"));
        this.kcal = new NumberField(getTranslation("product-form.kcal-label"));
        this.type = new ComboBox<>(getTranslation("product-form.type-label"));
        this.type.setItems(ProductType.values());
        this.type.setItemLabelGenerator(e -> getTranslation(e.getTranslationKey()));

        this.binder = new BeanValidationBinder<>(Product.class);
        this.binder.setBean(product);
        this.binder.bindInstanceFields(this);

        this.add(name, 2);
        this.add(kcal, type);
        createSubmitButtons();
    }

    private void createSubmitButtons() {
        var save = new Button(getTranslation("form.save"));
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(event -> validateAndSave());

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        var buttonLayout = new HorizontalLayout(save);
        buttonLayout.addClassNames(LumoUtility.Margin.Top.MEDIUM);
        add(buttonLayout);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new ProductForm.SaveEvent(this, binder.getBean()));
        }
    }

    @Getter
    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {

        private Product product;

        protected ProductFormEvent(ProductForm source, Product product) {
            super(source, false);
            this.product = product;
        }
    }

    public static class SaveEvent extends ProductForm.ProductFormEvent {
        SaveEvent(ProductForm source, Product product) {
            super(source, product);
        }
    }

    public Registration addSaveListener(ComponentEventListener<ProductForm.SaveEvent> listener) {
        return addListener(ProductForm.SaveEvent.class, listener);
    }
}
