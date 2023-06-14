package pl.daveproject.frontendservice.uiComponents;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class WebdietFormLayoutWrapper extends VerticalLayout {

    private final FormLayout formLayout;

    public WebdietFormLayoutWrapper(String formTitleKey) {
        this(formTitleKey, true);
    }

    public WebdietFormLayoutWrapper(String formTitleKey,
                                    boolean mediumWidth) {
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.formLayout = new FormLayout();
        add(new H2(getTranslation(formTitleKey)));
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        formLayout.setClassName(getCssClassName(mediumWidth));
        add(formLayout);
    }

    private String getCssClassName(boolean mediumWidth) {
        if (mediumWidth) {
            return "medium-width-form";
        }
        return "full-width-form";
    }
}
