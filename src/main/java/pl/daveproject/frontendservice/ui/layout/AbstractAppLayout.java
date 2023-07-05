package pl.daveproject.frontendservice.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

@CssImport("./style.css")
@JsModule("./prefers-color-scheme.js")
public class AbstractAppLayout extends AppLayout {

    public AbstractAppLayout() {
        addToNavbar(createHeader());
    }

    private HorizontalLayout createHeader() {
        var applicationTitle = new H1(getTranslation("application.name"));
        applicationTitle.addClassNames(LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(applicationTitle);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        return header;
    }
}
