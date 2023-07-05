package pl.daveproject.frontendservice.ui.layout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import pl.daveproject.frontendservice.applicationUser.ApplicationUserService;
import pl.daveproject.frontendservice.exception.UserNotLoginException;
import pl.daveproject.frontendservice.ui.component.WebdietNotification;
import pl.daveproject.frontendservice.ui.component.type.WebdietNotificationType;
import pl.daveproject.frontendservice.ui.login.LoginView;
import pl.daveproject.frontendservice.ui.login.service.LoginService;

@Slf4j
public class AfterLoginAppLayout extends AbstractAppLayout {

    private final ApplicationUserService applicationUserService;
    private final LoginService loginService;

    public AfterLoginAppLayout(ApplicationUserService applicationUserService,
                               LoginService loginService) {
        super();
        this.applicationUserService = applicationUserService;
        this.loginService = loginService;
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

        var layout = new HorizontalLayout(createAvatarMenuBar(avatar));
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        layout.addClassNames(LumoUtility.Margin.Right.MEDIUM);
        return layout;
    }

    private MenuBar createAvatarMenuBar(Avatar avatar) {
        var menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        var subMenu = menuBar.addItem(avatar).getSubMenu();
        subMenu.addItem(getTranslation("avatar.settings"), e -> {
            //TODO: Create application settings view
            UI.getCurrent().navigate(EmptyView.class);
        });
        subMenu.addItem(getTranslation("avatar.logout"), e -> {
            loginService.logout();
            UI.getCurrent().navigate(LoginView.class);
        });
        return menuBar;
    }
}
