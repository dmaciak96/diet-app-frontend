package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;

public class CloseableDialog extends Dialog {

    public CloseableDialog(String title, boolean isTranslationKey) {
        if(isTranslationKey) {
            this.setHeaderTitle(getTranslation(title));
        } else {
            this.setHeaderTitle(title);
        }
        var closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getHeader().add(closeButton);
        this.setWidth("50%");
    }

    public CloseableDialog(String translationKey) {
        this(translationKey, true);
    }
}
