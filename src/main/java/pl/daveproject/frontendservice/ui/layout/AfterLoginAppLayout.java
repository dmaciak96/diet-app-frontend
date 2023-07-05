package pl.daveproject.frontendservice.ui.layout;

import com.vaadin.flow.component.avatar.Avatar;
import org.apache.commons.lang3.NotImplementedException;


public class AfterLoginAppLayout extends AbstractAppLayout {



    public AfterLoginAppLayout() {
        super();
        addToNavbar(createAvatar());
    }

    private Avatar createAvatar() {
        throw new NotImplementedException();
    }
}
