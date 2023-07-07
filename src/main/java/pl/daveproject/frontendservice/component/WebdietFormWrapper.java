package pl.daveproject.frontendservice.component;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class WebdietFormWrapper extends VerticalLayout {

    private final FormLayout formLayout;

    public WebdietFormWrapper(FormLayout formLayout, boolean mediumWidth) {
        this(null, formLayout, mediumWidth);
    }

    public WebdietFormWrapper(String formTitleKey, FormLayout formLayout) {
        this(formTitleKey, formLayout, true);
    }

    public WebdietFormWrapper(String formTitleKey,
                              FormLayout formLayout,
                              boolean mediumWidth) {
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
        this.formLayout = formLayout;
        if (formTitleKey != null) {
            add(new H2(getTranslation(formTitleKey)));
        }
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
