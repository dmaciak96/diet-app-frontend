package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;

public class CloseableDialog extends Dialog {

    public CloseableDialog(String titleTranslationKey) {
        this.setHeaderTitle(getTranslation(titleTranslationKey));
        var closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getHeader().add(closeButton);
    }
}
