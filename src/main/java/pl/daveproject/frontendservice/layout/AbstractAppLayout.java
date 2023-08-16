package pl.daveproject.frontendservice.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pl.daveproject.frontendservice.bmi.BmiView;
import pl.daveproject.frontendservice.dashboard.DashboardView;

@CssImport("./style.css")
@JsModule("./prefers-color-scheme.js")
public class AbstractAppLayout extends AppLayout {

    public AbstractAppLayout() {
        this(false, false);
    }

    public AbstractAppLayout(boolean withDrawerToggle, boolean withNavbar) {
        if(withDrawerToggle) {
            addToNavbar(new DrawerToggle());
        }
        if(withNavbar) {
            addToNavbar(createHeader());
        }
    }

    private HorizontalLayout createHeader() {
        var imageResource = new StreamResource("logo.png",
                () -> getClass().getResourceAsStream("/img/logo.png"));

        var image = new Image(imageResource, "Webdiet logo");
        image.addClickListener(e -> UI.getCurrent().navigate(DashboardView.class));
        image.addClassNames(LumoUtility.Margin.MEDIUM, LumoUtility.Height.MEDIUM);

        var header = new HorizontalLayout(image);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        return header;
    }
}
