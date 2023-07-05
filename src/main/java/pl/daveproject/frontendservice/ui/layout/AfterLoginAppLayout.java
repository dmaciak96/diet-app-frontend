package pl.daveproject.frontendservice.ui.layout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.exception.UserNotLoginException;
import pl.daveproject.frontendservice.ui.component.WebdietNotification;
import pl.daveproject.frontendservice.ui.component.type.WebdietNotificationType;

@Slf4j
public class AfterLoginAppLayout extends AbstractAppLayout {

    private final ApplicationUserService applicationUserService;

    public AfterLoginAppLayout(ApplicationUserService applicationUserService) {
        super();
        this.applicationUserService = applicationUserService;

        addToNavbar(createAvatar());
    }

    private HorizontalLayout createAvatar() {
        var avatar = new Avatar();
        try {
            var currentUser = applicationUserService.findCurrentUser();
            avatar = new Avatar(currentUser.getFullName());
        } catch (JsonProcessingException e) {
            WebdietNotification.show(getTranslation("error-message.unexpected"), WebdietNotificationType.ERROR);
            log.error("Jwt token processing error: ", e);
        } catch (UserNotLoginException e) {
            WebdietNotification.show(getTranslation("error-message.user-not-login"), WebdietNotificationType.ERROR);
            log.error("Login user not found for current session: ", e);
        }

        var layout = new HorizontalLayout(avatar);
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.addClassNames(LumoUtility.Margin.Right.MEDIUM);
        return layout;
    }
}
